type AccountBase = {
  fullName: string;
  email: string;
};

type Passenger = AccountBase & {
  type: "passenger";
  walletBalanceGrosze: number;
  phoneNumber: string;
};

type Inspector = AccountBase & { type: "inspector" };

export type Account = Passenger | Inspector;

export type Identity = {
  account: Account;
  jwt: string;
};
