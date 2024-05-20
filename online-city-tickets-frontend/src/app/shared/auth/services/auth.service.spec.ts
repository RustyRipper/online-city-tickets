import { provide } from "~/shared/testing";

import { AuthService } from "./auth.service";

const JWT =
  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

describe(AuthService.name, () => {
  it("should read JWT and account type from storage", () => {
    const { sut } = provide(AuthService, {
      storage: {
        JWT: JSON.stringify(JWT),
        ACCOUNT_TYPE: JSON.stringify("passenger"),
      },
    });

    expect(sut.jwt).toBe(JWT);
    expect(sut.accountType).toBe("passenger");
  });

  it("should clear JWT and account type on logout", () => {
    const { sut } = provide(AuthService, {
      storage: {
        JWT: JSON.stringify(JWT),
        ACCOUNT_TYPE: JSON.stringify("passenger"),
      },
    });

    sut.logout();

    expect(sut.jwt).toBeNull();
    expect(sut.accountType).toBeNull();
  });
});
