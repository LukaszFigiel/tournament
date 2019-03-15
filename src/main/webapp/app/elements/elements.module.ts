import { NgModule } from '@angular/core';
import { GroupsComponent } from './groups/groups.component';
import { TournamentSharedModule } from '../shared/shared.module';
import { MatchesComponent } from './matches/matches.component';

@NgModule({
    imports: [TournamentSharedModule],
    declarations: [GroupsComponent, MatchesComponent],
    exports: [GroupsComponent, MatchesComponent]
})
export class TournamentElementsModule {}
