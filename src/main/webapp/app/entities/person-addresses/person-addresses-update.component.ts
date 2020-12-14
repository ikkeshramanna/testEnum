import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPersonAddresses, PersonAddresses } from 'app/shared/model/person-addresses.model';
import { PersonAddressesService } from './person-addresses.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person/person.service';

@Component({
  selector: 'jhi-person-addresses-update',
  templateUrl: './person-addresses-update.component.html',
})
export class PersonAddressesUpdateComponent implements OnInit {
  isSaving = false;
  people: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    address: [null, [Validators.required]],
    zipcode: [],
    test: [],
    person: [],
  });

  constructor(
    protected personAddressesService: PersonAddressesService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personAddresses }) => {
      this.updateForm(personAddresses);

      this.personService.query().subscribe((res: HttpResponse<IPerson[]>) => (this.people = res.body || []));
    });
  }

  updateForm(personAddresses: IPersonAddresses): void {
    this.editForm.patchValue({
      id: personAddresses.id,
      address: personAddresses.address,
      zipcode: personAddresses.zipcode,
      test: personAddresses.test,
      person: personAddresses.person,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personAddresses = this.createFromForm();
    if (personAddresses.id !== undefined) {
      this.subscribeToSaveResponse(this.personAddressesService.update(personAddresses));
    } else {
      this.subscribeToSaveResponse(this.personAddressesService.create(personAddresses));
    }
  }

  private createFromForm(): IPersonAddresses {
    return {
      ...new PersonAddresses(),
      id: this.editForm.get(['id'])!.value,
      address: this.editForm.get(['address'])!.value,
      zipcode: this.editForm.get(['zipcode'])!.value,
      test: this.editForm.get(['test'])!.value,
      person: this.editForm.get(['person'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonAddresses>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPerson): any {
    return item.id;
  }
}
