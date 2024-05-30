import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import {
  FormBuilder,
  ReactiveFormsModule,
  type ValidatorFn,
  Validators,
} from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DividerModule } from "primeng/divider";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";

import { AuthService } from "~/shared/auth/services/auth.service";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { Form } from "~/shared/forms/form";
import { I18nService } from "~/shared/i81n/i18n.service";

const repeatValidator: (name: string) => ValidatorFn = (name) => (control) =>
  control.value === control.parent?.get(name)?.value ? null : { repeat: true };

@Component({
  selector: "app-register-page",
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
  templateUrl: "./register-page.component.html",
  styleUrl: "../../auth-form.css",
})
export class RegisterPageComponent {
  public readonly form;

  public constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    protected readonly i18n: I18nService,
    formBuilder: FormBuilder,
  ) {
    this.form = new Form(
      formBuilder.nonNullable.group({
        fullName: ["", [Validators.required, Validators.minLength(1)]],
        email: ["", [Validators.required, Validators.email]],
        password: ["", [Validators.required, Validators.minLength(8)]],
        repeat: ["", [repeatValidator("password")]],
      }),
    );
  }

  protected async onSubmit() {
    this.form.submit(async () => {
      await this.authService.register(this.form.value);
      this.router.navigateByUrl("/passenger");
    });
  }
}
