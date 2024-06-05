import { Component } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { MessageService } from "primeng/api";
import { ButtonModule } from "primeng/button";
import { InputMaskModule } from "primeng/inputmask";
import { InputTextModule } from "primeng/inputtext";
import { firstValueFrom } from "rxjs";

import { CardsApi } from "~/generated/api/services";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { Form } from "~/shared/forms/form";
import { I18nService } from "~/shared/i81n/i18n.service";

import { CreditCard } from "../../model";

@Component({
  selector: "app-card-details-page",
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    InputMaskModule,
    ButtonModule,
    TopBarComponent,
    BackButtonComponent,
  ],
  templateUrl: "./card-details-page.component.html",
  styleUrl: "./card-details-page.component.css",
})
export class CardDetailsPageComponent {
  protected readonly card: CreditCard;

  protected readonly form;

  public constructor(
    private readonly cardsApi: CardsApi,
    private readonly messageService: MessageService,
    private readonly router: Router,
    protected readonly i18n: I18nService,
    activatedRoute: ActivatedRoute,
    formBuilder: FormBuilder,
  ) {
    this.card = activatedRoute.snapshot.data["card"];

    this.form = new Form(
      formBuilder.nonNullable.group({
        label: [this.card.customLabel ?? ""],
        expirationDate: [
          this.card.expirationDate,
          [
            Validators.required,
            Validators.pattern(/^(0[1-9]|1[0-2])\/[0-9]{2}$/),
          ],
        ],
      }),
    );
  }

  protected onSubmit(): void {
    this.form.submit(async () => {
      await firstValueFrom(
        this.cardsApi.updateCreditCard({
          id: String(this.card.id),
          body: this.form.value,
        }),
      );
      this.messageService.add({
        severity: "success",
        summary: this.i18n.t("card-details-page.saved-successfully"),
      });
    });
  }

  protected onDelete(): void {
    this.cardsApi.deleteCreditCard({ id: String(this.card.id) }).subscribe();
    this.router.navigateByUrl("/passenger/credit-cards");
  }
}
