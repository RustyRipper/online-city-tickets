import { AbstractControl, FormGroup } from "@angular/forms";

type Status = "idle" | "loading" | "error";

export class Form<T extends { [K in keyof T]: AbstractControl<any, any> }> {
  private status: Status = "idle";

  public constructor(public readonly group: FormGroup<T>) {}

  public get value() {
    return this.group.getRawValue();
  }

  public get isLoading() {
    return this.status === "loading";
  }

  public get isError() {
    return this.status === "error";
  }

  public get isSubmitDisabled() {
    return this.group.invalid || this.group.pristine;
  }

  public showError(control: keyof T & string) {
    return Boolean(
      this.group.get(control)?.touched && this.group.get(control)?.errors,
    );
  }

  public submit(action: () => Promise<void>) {
    this.status = "loading";
    action()
      .then(() => {
        this.status = "idle";
      })
      .catch(() => {
        this.status = "error";
      });
  }
}
