import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestEnumTestModule } from '../../../test.module';
import { PersonDetailsComponent } from 'app/entities/person-details/person-details.component';
import { PersonDetailsService } from 'app/entities/person-details/person-details.service';
import { PersonDetails } from 'app/shared/model/person-details.model';

describe('Component Tests', () => {
  describe('PersonDetails Management Component', () => {
    let comp: PersonDetailsComponent;
    let fixture: ComponentFixture<PersonDetailsComponent>;
    let service: PersonDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestEnumTestModule],
        declarations: [PersonDetailsComponent],
      })
        .overrideTemplate(PersonDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PersonDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personDetails && comp.personDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
