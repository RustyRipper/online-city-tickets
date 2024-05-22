import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DividerModule } from "primeng/divider";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";

import { AuthService } from "~/shared/auth/services/auth.service";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";

@Component({
  selector: "app-login-page",
  standalone: true,
  imports: [
    CommonModule,
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
  private status: "idle" | "loading" | "error" = "idle";

  public readonly form;

  public constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    protected readonly i18n: I18nService,
    formBuilder: FormBuilder,
  ) {
    this.form = formBuilder.nonNullable.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(8)]],
    });
  }

  protected get isLoading() {
    return this.status === "loading";
  }

  protected get isError() {
    return this.status === "error";
  }

  public async onSubmit() {
    this.status = "loading";
    const account = await this.authService.login(this.form.getRawValue());
    this.status = account ? "idle" : "error";
    if (account) {
      this.router.navigateByUrl("/passenger");
    }
  }
}
