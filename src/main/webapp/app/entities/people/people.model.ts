export interface IPeople {
  id: number;
  firstname?: string | null;
  lastname?: string | null;
}

export type NewPeople = Omit<IPeople, 'id'> & { id: null };
