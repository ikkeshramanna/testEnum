import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PersonAddressesService } from 'app/entities/person-addresses/person-addresses.service';
import { IPersonAddresses, PersonAddresses } from 'app/shared/model/person-addresses.model';
import { TestType } from 'app/shared/model/enumerations/test-type.model';

describe('Service Tests', () => {
  describe('PersonAddresses Service', () => {
    let injector: TestBed;
    let service: PersonAddressesService;
    let httpMock: HttpTestingController;
    let elemDefault: IPersonAddresses;
    let expectedResult: IPersonAddresses | IPersonAddresses[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PersonAddressesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PersonAddresses(0, 'AAAAAAA', 0, TestType.AT_SUPPLEMENTARY_FEEDERS);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PersonAddresses', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PersonAddresses()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PersonAddresses', () => {
        const returnedFromService = Object.assign(
          {
            address: 'BBBBBB',
            zipcode: 1,
            test: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PersonAddresses', () => {
        const returnedFromService = Object.assign(
          {
            address: 'BBBBBB',
            zipcode: 1,
            test: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PersonAddresses', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
