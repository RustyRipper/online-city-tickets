type AccountBase = {
  fullName: string;
  email: string;
};

type Passenger = AccountBase & {
  type: "passenger";
  walletBalanceGrosze: number;
  phoneNumber?: string;
  defaultCreditCardId?: number;
};

type Inspector = AccountBase & { type: "inspector" };

export type Account = Passenger | Inspector;
