import {Transaction} from './transaction.model';

export class Account{
  balance: number;
  identifier: string;
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
  transactions: Transaction[] = [];
}
