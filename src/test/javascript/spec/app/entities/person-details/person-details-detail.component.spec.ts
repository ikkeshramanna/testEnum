import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestEnumTestModule } from '../../../test.module';
import { PersonDetailsDetailComponent } from 'app/entities/person-details/person-details-detail.component';
import { PersonDetails } from 'app/shared/model/person-details.model';

describe('Component Tests', () => {
  describe('PersonDetails Management Detail Component', () => {
    let comp: PersonDetailsDetailComponent;
    let fixture: ComponentFixture<PersonDetailsDetailComponent>;
    const route = ({ data: of({ personDetails: new PersonDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestEnumTestModule],
        declarations: [PersonDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PersonDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load personDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
