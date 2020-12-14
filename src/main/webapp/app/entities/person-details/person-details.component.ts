import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonDetails } from 'app/shared/model/person-details.model';
import { PersonDetailsService } from './person-details.service';
import { PersonDetailsDeleteDialogComponent } from './person-details-delete-dialog.component';

@Component({
  selector: 'jhi-person-details',
  templateUrl: './person-details.component.html',
})
export class PersonDetailsComponent implements OnInit, OnDestroy {
  personDetails?: IPersonDetails[];
  eventSubscriber?: Subscription;

  constructor(
    protected personDetailsService: PersonDetailsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.personDetailsService.query().subscribe((res: HttpResponse<IPersonDetails[]>) => (this.personDetails = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPersonDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPersonDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPersonDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('personDetailsListModification', () => this.loadAll());
  }

  delete(personDetails: IPersonDetails): void {
    const modalRef = this.modalService.open(PersonDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.personDetails = personDetails;
  }
}
