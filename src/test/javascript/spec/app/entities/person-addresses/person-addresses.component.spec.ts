import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestEnumTestModule } from '../../../test.module';
import { PersonAddressesComponent } from 'app/entities/person-addresses/person-addresses.component';
import { PersonAddressesService } from 'app/entities/person-addresses/person-addresses.service';
import { PersonAddresses } from 'app/shared/model/person-addresses.model';

describe('Component Tests', () => {
  describe('PersonAddresses Management Component', () => {
    let comp: PersonAddressesComponent;
    let fixture: ComponentFixture<PersonAddressesComponent>;
    let service: PersonAddressesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestEnumTestModule],
        declarations: [PersonAddressesComponent],
      })
        .overrideTemplate(PersonAddressesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonAddressesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonAddressesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PersonAddresses(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personAddresses && comp.personAddresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
