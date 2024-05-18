import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import {
  type ValidatorFn,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DividerModule } from "primeng/divider";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";

import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { AuthService } from "~/shared/auth/services/auth.service";

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
  private status: "idle" | "loading" | "error" = "idle";

  protected readonly form;

  public constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    formBuilder: FormBuilder,
  ) {
    this.form = formBuilder.nonNullable.group({
      fullName: ["", [Validators.required, Validators.minLength(1)]],
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(8)]],
      repeat: ["", [repeatValidator("password")]],
    });
  }

  protected get isLoading() {
    return this.status === "loading";
  }

  protected get isError() {
    return this.status === "error";
  }

  protected async onSubmit() {
    this.status = "loading";
    const account = await this.authService.register(this.form.getRawValue());
    this.status = account ? "idle" : "error";
    if (account) {
      this.router.navigateByUrl("/passenger");
    }
  }
}
