import { IPeople } from 'app/entities/people/people.model';

export interface IAddress {
  id: number;
  street?: string | null;
  city?: string | null;
  state?: string | null;
  zip?: string | null;
  people?: IPeople | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
