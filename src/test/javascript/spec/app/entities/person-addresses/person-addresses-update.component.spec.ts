import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestEnumTestModule } from '../../../test.module';
import { PersonAddressesUpdateComponent } from 'app/entities/person-addresses/person-addresses-update.component';
import { PersonAddressesService } from 'app/entities/person-addresses/person-addresses.service';
import { PersonAddresses } from 'app/shared/model/person-addresses.model';

describe('Component Tests', () => {
  describe('PersonAddresses Management Update Component', () => {
    let comp: PersonAddressesUpdateComponent;
    let fixture: ComponentFixture<PersonAddressesUpdateComponent>;
    let service: PersonAddressesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestEnumTestModule],
        declarations: [PersonAddressesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PersonAddressesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonAddressesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonAddressesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonAddresses(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonAddresses();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
