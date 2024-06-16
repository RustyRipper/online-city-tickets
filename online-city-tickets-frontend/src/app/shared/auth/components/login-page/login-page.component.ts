import { Component } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DividerModule } from "primeng/divider";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";

import { AuthService } from "~/shared/auth/services/auth.service";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { Form } from "~/shared/forms/form";
import { I18nService } from "~/shared/i18n/i18n.service";

@Component({
  selector: "app-login-page",
  standalone: true,
  imports: [
    RouterModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    PasswordModule,
    DividerModule,
    TopBarComponent,
  ],
  templateUrl: "./login-page.component.html",
  styleUrl: "../../auth-form.css",
})
export class LoginPageComponent {
  public readonly form;

  public constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    protected readonly i18n: I18nService,
    formBuilder: FormBuilder,
  ) {
    this.form = new Form(
      formBuilder.nonNullable.group({
        email: ["", [Validators.required, Validators.email]],
        password: ["", [Validators.required, Validators.minLength(8)]],
      }),
    );
  }

  public onSubmit() {
    this.form.submit(async () => {
      await this.authService.login(this.form.value);
      this.router.navigateByUrl("/passenger");
    });
  }
}
