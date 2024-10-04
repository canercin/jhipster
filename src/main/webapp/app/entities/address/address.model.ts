export interface IAddress {
  id: number;
  street?: string | null;
  city?: string | null;
  state?: string | null;
  zip?: string | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
