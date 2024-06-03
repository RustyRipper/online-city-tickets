import { mount } from "~/shared/testing";

import { TicketListPageComponent } from "./ticket-list-page.component";

describe(TicketListPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(TicketListPageComponent);

    expect(sut).toBeTruthy();
  });
});
