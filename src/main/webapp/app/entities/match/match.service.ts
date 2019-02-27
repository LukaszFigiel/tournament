import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMatch } from 'app/shared/model/match.model';

type EntityResponseType = HttpResponse<IMatch>;
type EntityArrayResponseType = HttpResponse<IMatch[]>;

@Injectable({ providedIn: 'root' })
export class MatchService {
    public resourceUrl = SERVER_API_URL + 'api/matches';

    constructor(protected http: HttpClient) {}

    create(match: IMatch): Observable<EntityResponseType> {
        return this.http.post<IMatch>(this.resourceUrl, match, { observe: 'response' });
    }

    update(match: IMatch): Observable<EntityResponseType> {
        return this.http.put<IMatch>(this.resourceUrl, match, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMatch>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMatch[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
