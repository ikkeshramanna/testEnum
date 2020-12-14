import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPersonDetails, PersonDetails } from 'app/shared/model/person-details.model';
import { PersonDetailsService } from './person-details.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person/person.service';

@Component({
  selector: 'jhi-person-details-update',
  templateUrl: './person-details-update.component.html',
})
export class PersonDetailsUpdateComponent implements OnInit {
  isSaving = false;
  people: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    nid: [null, [Validators.required]],
    dob: [null, [Validators.required]],
    person: [],
  });

  constructor(
    protected personDetailsService: PersonDetailsService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personDetails }) => {
      this.updateForm(personDetails);

      this.personService.query().subscribe((res: HttpResponse<IPerson[]>) => (this.people = res.body || []));
    });
  }

  updateForm(personDetails: IPersonDetails): void {
    this.editForm.patchValue({
      id: personDetails.id,
      nid: personDetails.nid,
      dob: personDetails.dob,
      person: personDetails.person,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personDetails = this.createFromForm();
    if (personDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.personDetailsService.update(personDetails));
    } else {
      this.subscribeToSaveResponse(this.personDetailsService.create(personDetails));
    }
  }

  private createFromForm(): IPersonDetails {
    return {
      ...new PersonDetails(),
      id: this.editForm.get(['id'])!.value,
      nid: this.editForm.get(['nid'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      person: this.editForm.get(['person'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonDetails>>): void {
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
