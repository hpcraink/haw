//import './polyfills';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';

import { AppModule } from "./app.module";

// Enable production mode unless running locally
if (!/loclahost/.test(document.location.host)) { enableProdMode(); }

platformBrowserDynamic().bootstrapModule(AppModule);
