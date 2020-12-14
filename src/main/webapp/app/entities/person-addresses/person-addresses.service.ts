import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonAddresses } from 'app/shared/model/person-addresses.model';

type EntityResponseType = HttpResponse<IPersonAddresses>;
type EntityArrayResponseType = HttpResponse<IPersonAddresses[]>;

@Injectable({ providedIn: 'root' })
export class PersonAddressesService {
  public resourceUrl = SERVER_API_URL + 'api/person-addresses';

  constructor(protected http: HttpClient) {}

  create(personAddresses: IPersonAddresses): Observable<EntityResponseType> {
    return this.http.post<IPersonAddresses>(this.resourceUrl, personAddresses, { observe: 'response' });
  }

  update(personAddresses: IPersonAddresses): Observable<EntityResponseType> {
    return this.http.put<IPersonAddresses>(this.resourceUrl, personAddresses, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonAddresses>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonAddresses[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
