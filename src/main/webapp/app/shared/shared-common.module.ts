import { NgModule } from '@angular/core';

import { TournamentSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [TournamentSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TournamentSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TournamentSharedCommonModule {}
