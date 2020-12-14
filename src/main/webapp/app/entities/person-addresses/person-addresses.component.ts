import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonAddresses } from 'app/shared/model/person-addresses.model';
import { PersonAddressesService } from './person-addresses.service';
import { PersonAddressesDeleteDialogComponent } from './person-addresses-delete-dialog.component';

@Component({
  selector: 'jhi-person-addresses',
  templateUrl: './person-addresses.component.html',
})
export class PersonAddressesComponent implements OnInit, OnDestroy {
  personAddresses?: IPersonAddresses[];
  eventSubscriber?: Subscription;

  constructor(
    protected personAddressesService: PersonAddressesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.personAddressesService.query().subscribe((res: HttpResponse<IPersonAddresses[]>) => (this.personAddresses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPersonAddresses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPersonAddresses): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPersonAddresses(): void {
    this.eventSubscriber = this.eventManager.subscribe('personAddressesListModification', () => this.loadAll());
  }

  delete(personAddresses: IPersonAddresses): void {
    const modalRef = this.modalService.open(PersonAddressesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.personAddresses = personAddresses;
  }
}
