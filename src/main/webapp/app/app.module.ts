import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TestEnumSharedModule } from 'app/shared/shared.module';
import { TestEnumCoreModule } from 'app/core/core.module';
import { TestEnumAppRoutingModule } from './app-routing.module';
import { TestEnumHomeModule } from './home/home.module';
import { TestEnumEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TestEnumSharedModule,
    TestEnumCoreModule,
    TestEnumHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TestEnumEntityModule,
    TestEnumAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class TestEnumAppModule {}
