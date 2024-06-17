import { Component } from "@angular/core";
import { ButtonModule } from "primeng/button";
import { InputGroupModule } from "primeng/inputgroup";
import { InputMaskModule } from "primeng/inputmask";
import { StepperModule } from "primeng/stepper";

import { CardDisplayComponent } from "~/passenger/credit-cards/components/card-display/card-display.component";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i18n/i18n.service";

@Component({
  selector: "app-card-addition-page",
  standalone: true,
  imports: [
    ButtonModule,
    InputGroupModule,
    InputMaskModule,
    StepperModule,
    TopBarComponent,
    BackButtonComponent,
    CardDisplayComponent,
  ],
  templateUrl: "./card-addition-page.component.html",
  styleUrl: "./card-addition-page.component.css",
})
export class CardAdditionPageComponent {
  public constructor(protected readonly i18n: I18nService) {}
}
