import {Component} from "@angular/core"

@Component({
  selector: 'my-app', 
  template: `
   <input #phone placeholder="phone number">
   <button (click)="callPhone(phone{caret}.value)">Call</button> 

   <input ref-fax placeholder="fax number">
   <button (click)="callFax(fax.value)">Fax</button>
  `
})
export class AppComponent {
  title = 'Tour of Heroes';
  heroes = [];
  selectedHero = { firstName: "eee" }
}
