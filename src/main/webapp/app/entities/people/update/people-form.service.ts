import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPeople, NewPeople } from '../people.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPeople for edit and NewPeopleFormGroupInput for create.
 */
type PeopleFormGroupInput = IPeople | PartialWithRequiredKeyOf<NewPeople>;

type PeopleFormDefaults = Pick<NewPeople, 'id'>;

type PeopleFormGroupContent = {
  id: FormControl<IPeople['id'] | NewPeople['id']>;
  firstname: FormControl<IPeople['firstname']>;
  lastname: FormControl<IPeople['lastname']>;
};

export type PeopleFormGroup = FormGroup<PeopleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PeopleFormService {
  createPeopleFormGroup(people: PeopleFormGroupInput = { id: null }): PeopleFormGroup {
    const peopleRawValue = {
      ...this.getFormDefaults(),
      ...people,
    };
    return new FormGroup<PeopleFormGroupContent>({
      id: new FormControl(
        { value: peopleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      firstname: new FormControl(peopleRawValue.firstname),
      lastname: new FormControl(peopleRawValue.lastname),
    });
  }

  getPeople(form: PeopleFormGroup): IPeople | NewPeople {
    return form.getRawValue() as IPeople | NewPeople;
  }

  resetForm(form: PeopleFormGroup, people: PeopleFormGroupInput): void {
    const peopleRawValue = { ...this.getFormDefaults(), ...people };
    form.reset(
      {
        ...peopleRawValue,
        id: { value: peopleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PeopleFormDefaults {
    return {
      id: null,
    };
  }
}
