import { NgModule } from '@angular/core';
import { GroupsComponent } from './groups/groups.component';
import { TournamentSharedModule } from '../shared/shared.module';

@NgModule({
    imports: [TournamentSharedModule],
    declarations: [GroupsComponent],
    exports: [GroupsComponent]
})
export class TournamentElementsModule {}
