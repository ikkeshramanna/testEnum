import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestEnumTestModule } from '../../../test.module';
import { PersonAddressesDetailComponent } from 'app/entities/person-addresses/person-addresses-detail.component';
import { PersonAddresses } from 'app/shared/model/person-addresses.model';

describe('Component Tests', () => {
  describe('PersonAddresses Management Detail Component', () => {
    let comp: PersonAddressesDetailComponent;
    let fixture: ComponentFixture<PersonAddressesDetailComponent>;
    const route = ({ data: of({ personAddresses: new PersonAddresses(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestEnumTestModule],
        declarations: [PersonAddressesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PersonAddressesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonAddressesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load personAddresses on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personAddresses).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
