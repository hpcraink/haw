import { platformBrowser } from '@angular/platform-browser';
import { enableProdMode } from '@angular/core';

import { AppModuleNgFactory } from "../public/aot/src/app.module.ngfactory";

// Enable production mode unless running locally
if (!/loclahost/.test(document.location.host)) { enableProdMode(); }

platformBrowser().bootstrapModuleFactory(AppModuleNgFactory);
