import { mount } from "~/shared/testing";

import { PaymentSheetComponent } from "./payment-sheet.component";

describe(PaymentSheetComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(PaymentSheetComponent);

    expect(sut).toBeTruthy();
  });
});
