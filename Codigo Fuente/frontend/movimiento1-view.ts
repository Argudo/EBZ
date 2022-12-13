import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/text-field/src/vaadin-text-field.js';

@customElement('movimiento1-view')
export class Movimiento1View extends LitElement {
  static get styles() {
    return css`
      :host {
          display: block;
          height: 100%;
      }
      `;
  }

  render() {
    return html`
Text  Text  Text 
<vaadin-text-field dir="rtl" label="???? ????" placeholder="???? ???????? "></vaadin-text-field>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
