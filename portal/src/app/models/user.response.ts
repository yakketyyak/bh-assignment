import {User} from './user.model';

export class UserResponse {
  content: User[] = [];
  number: number;
  totalElements: number;
  size: number;
}
