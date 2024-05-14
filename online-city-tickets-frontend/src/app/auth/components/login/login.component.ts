import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router, RouterModule } from "@angular/router";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";
import { DividerModule } from "primeng/divider";

import { TopBarComponent } from "../../../shared/components/top-bar/top-bar.component";
import { AuthService } from "../../services/auth.service";

@Component({
  selector: "app-login",
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
  templateUrl: "./login.component.html",
  styleUrl: "../../auth-form.css",
})
export class LoginComponent {
  private status: "idle" | "loading" | "error" = "idle";

  protected readonly form;

  public constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
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

  protected async onSubmit() {
    this.status = "loading";
    const account = await this.authService.login(this.form.getRawValue());
    this.status = account ? "idle" : "error";
    if (account) {
      this.router.navigateByUrl("/passenger");
    }
  }
}
