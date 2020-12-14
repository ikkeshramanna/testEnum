import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonAddresses } from 'app/shared/model/person-addresses.model';

@Component({
  selector: 'jhi-person-addresses-detail',
  templateUrl: './person-addresses-detail.component.html',
})
export class PersonAddressesDetailComponent implements OnInit {
  personAddresses: IPersonAddresses | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personAddresses }) => (this.personAddresses = personAddresses));
  }

  previousState(): void {
    window.history.back();
  }
}
