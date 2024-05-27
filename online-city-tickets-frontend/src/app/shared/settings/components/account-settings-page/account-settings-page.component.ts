import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { InputMaskModule } from "primeng/inputmask";
import { InputTextModule } from "primeng/inputtext";
import { firstValueFrom } from "rxjs";

import type { AccountDto, UpdateAccountReq } from "~/generated/api/models";
import { AccountsApi } from "~/generated/api/services";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { Form } from "~/shared/forms/form";
import { I18nService } from "~/shared/i81n/i18n.service";

type Model = { fullName: string; phoneNumber?: string };

function dtoToModel(account: AccountDto): Model {
  return {
    fullName: account.fullName,
    phoneNumber:
      account.type === "passenger" && account.phoneNumber
        ? (account.phoneNumber.match(/[0-9]{3}/g) ?? []).join(" ")
        : undefined,
  };
}

function modelToDto(model: Model): UpdateAccountReq {
  const trimmedNumber = (model.phoneNumber ?? "").replace(/[ _]/g, "");
  return {
    fullName: model.fullName,
    phoneNumber: trimmedNumber === "" ? undefined : trimmedNumber,
  };
}

@Component({
  selector: "app-account-settings-page",
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    InputMaskModule,
    TopBarComponent,
    BackButtonComponent,
  ],
  templateUrl: "./account-settings-page.component.html",
  styleUrl: "../../../../shared/auth/auth-form.css",
})
export class AccountSettingsPageComponent {
  private account: AccountDto;

  public readonly form;

  public constructor(
    private readonly accountsApi: AccountsApi,
    protected readonly i18n: I18nService,
    activatedRoute: ActivatedRoute,
    formBuilder: FormBuilder,
  ) {
    this.account = activatedRoute.snapshot.data["account"];

    const initial = dtoToModel(this.account);
    this.form = new Form(
      formBuilder.nonNullable.group({
        fullName: [
          initial.fullName,
          [Validators.required, Validators.minLength(1)],
        ],
        phoneNumber: [
          initial.phoneNumber,
          [Validators.pattern(/[0-9]{3} [0-9]{3} [0-9]{3}/)],
        ],
      }),
    );
  }

  protected get isPassenger() {
    return this.account.type === "passenger";
  }

  protected detailsOnSubmit() {
    this.form.submit(async () => {
      this.account = await firstValueFrom(
        this.accountsApi.updateAccount({ body: modelToDto(this.form.value) }),
      );
    });
  }
}
