(self.webpackChunk_unified_inventory=self.webpackChunk_unified_inventory||[]).push([[850],{33922:(e,n,r)=>{"use strict";r.d(n,{u:()=>K});var t=r(22732),o=r.n(t),i=r(13980),a=r.n(i),u=r(20037),c=r(30387);function l(e,n){return function(e){if(Array.isArray(e))return e}(e)||function(e,n){if("undefined"!=typeof Symbol&&Symbol.iterator in Object(e)){var r=[],t=!0,o=!1,i=void 0;try{for(var a,u=e[Symbol.iterator]();!(t=(a=u.next()).done)&&(r.push(a.value),!n||r.length!==n);t=!0);}catch(e){o=!0,i=e}finally{try{t||null==u.return||u.return()}finally{if(o)throw i}}return r}}(e,n)||function(e,n){if(e){if("string"==typeof e)return s(e,n);var r=Object.prototype.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?s(e,n):void 0}}(e,n)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function s(e,n){(null==n||n>e.length)&&(n=e.length);for(var r=0,t=new Array(n);r<n;r++)t[r]=e[r];return t}var f=function(e){e=Array.isArray(e)&&1===e.length?e[0]:e;var n=Array.isArray(e)?"one of ".concat(e.join(", ")):e;return{code:"file-invalid-type",message:"File type must be ".concat(n)}},p=function(e){return{code:"file-too-large",message:"File is larger than ".concat(e," bytes")}},g=function(e){return{code:"file-too-small",message:"File is smaller than ".concat(e," bytes")}},d={code:"too-many-files",message:"Too many files"};function v(e,n){var r="application/x-moz-file"===e.type||(0,c.Z)(e,n);return[r,r?null:f(n)]}function y(e,n,r){if(b(e.size))if(b(n)&&b(r)){if(e.size>r)return[!1,p(r)];if(e.size<n)return[!1,g(n)]}else{if(b(n)&&e.size<n)return[!1,g(n)];if(b(r)&&e.size>r)return[!1,p(r)]}return[!0,null]}function b(e){return null!=e}function m(e){var n=e.files,r=e.accept,t=e.minSize,o=e.maxSize,i=e.multiple,a=e.maxFiles;return!(!i&&n.length>1||i&&a>=1&&n.length>a)&&n.every((function(e){var n=l(v(e,r),1)[0],i=l(y(e,t,o),1)[0];return n&&i}))}function D(e){return"function"==typeof e.isPropagationStopped?e.isPropagationStopped():void 0!==e.cancelBubble&&e.cancelBubble}function h(e){return e.dataTransfer?Array.prototype.some.call(e.dataTransfer.types,(function(e){return"Files"===e||"application/x-moz-file"===e})):!!e.target&&!!e.target.files}function F(e){e.preventDefault()}function O(e){return-1!==e.indexOf("MSIE")||-1!==e.indexOf("Trident/")}function A(e){return-1!==e.indexOf("Edge/")}function j(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:window.navigator.userAgent;return O(e)||A(e)}function w(){for(var e=arguments.length,n=new Array(e),r=0;r<e;r++)n[r]=arguments[r];return function(e){for(var r=arguments.length,t=new Array(r>1?r-1:0),o=1;o<r;o++)t[o-1]=arguments[o];return n.some((function(n){return!D(e)&&n&&n.apply(void 0,[e].concat(t)),D(e)}))}}function E(e){return function(e){if(Array.isArray(e))return k(e)}(e)||function(e){if("undefined"!=typeof Symbol&&Symbol.iterator in Object(e))return Array.from(e)}(e)||C(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function S(e,n){return function(e){if(Array.isArray(e))return e}(e)||function(e,n){if("undefined"!=typeof Symbol&&Symbol.iterator in Object(e)){var r=[],t=!0,o=!1,i=void 0;try{for(var a,u=e[Symbol.iterator]();!(t=(a=u.next()).done)&&(r.push(a.value),!n||r.length!==n);t=!0);}catch(e){o=!0,i=e}finally{try{t||null==u.return||u.return()}finally{if(o)throw i}}return r}}(e,n)||C(e,n)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function C(e,n){if(e){if("string"==typeof e)return k(e,n);var r=Object.prototype.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?k(e,n):void 0}}function k(e,n){(null==n||n>e.length)&&(n=e.length);for(var r=0,t=new Array(n);r<n;r++)t[r]=e[r];return t}function x(e,n){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var t=Object.getOwnPropertySymbols(e);n&&(t=t.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),r.push.apply(r,t)}return r}function P(e){for(var n=1;n<arguments.length;n++){var r=null!=arguments[n]?arguments[n]:{};n%2?x(Object(r),!0).forEach((function(n){z(e,n,r[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):x(Object(r)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(r,n))}))}return e}function z(e,n,r){return n in e?Object.defineProperty(e,n,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[n]=r,e}function R(e,n){if(null==e)return{};var r,t,o=function(e,n){if(null==e)return{};var r,t,o={},i=Object.keys(e);for(t=0;t<i.length;t++)r=i[t],n.indexOf(r)>=0||(o[r]=e[r]);return o}(e,n);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(t=0;t<i.length;t++)r=i[t],n.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(o[r]=e[r])}return o}var I=(0,t.forwardRef)((function(e,n){var r=e.children,i=K(R(e,["children"])),a=i.open,u=R(i,["open"]);return(0,t.useImperativeHandle)(n,(function(){return{open:a}}),[a]),o().createElement(t.Fragment,null,r(P(P({},u),{},{open:a})))}));I.displayName="Dropzone";var T={disabled:!1,getFilesFromEvent:u.R,maxSize:1/0,minSize:0,multiple:!0,maxFiles:0,preventDropOnDocument:!0,noClick:!1,noKeyboard:!1,noDrag:!1,noDragEventsBubbling:!1,validator:null};I.defaultProps=T,I.propTypes={children:a().func,accept:a().oneOfType([a().string,a().arrayOf(a().string)]),multiple:a().bool,preventDropOnDocument:a().bool,noClick:a().bool,noKeyboard:a().bool,noDrag:a().bool,noDragEventsBubbling:a().bool,minSize:a().number,maxSize:a().number,maxFiles:a().number,disabled:a().bool,getFilesFromEvent:a().func,onFileDialogCancel:a().func,onDragEnter:a().func,onDragLeave:a().func,onDragOver:a().func,onDrop:a().func,onDropAccepted:a().func,onDropRejected:a().func,validator:a().func};var L={isFocused:!1,isFileDialogActive:!1,isDragActive:!1,isDragAccept:!1,isDragReject:!1,draggedFiles:[],acceptedFiles:[],fileRejections:[]};function K(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},n=P(P({},T),e),r=n.accept,o=n.disabled,i=n.getFilesFromEvent,a=n.maxSize,u=n.minSize,c=n.multiple,l=n.maxFiles,s=n.onDragEnter,f=n.onDragLeave,p=n.onDragOver,g=n.onDrop,b=n.onDropAccepted,O=n.onDropRejected,A=n.onFileDialogCancel,C=n.preventDropOnDocument,k=n.noClick,x=n.noKeyboard,I=n.noDrag,K=n.noDragEventsBubbling,M=n.validator,_=(0,t.useRef)(null),N=(0,t.useRef)(null),U=(0,t.useReducer)(B,L),$=S(U,2),q=$[0],H=$[1],Z=q.isFocused,G=q.isFileDialogActive,J=q.draggedFiles,Q=(0,t.useCallback)((function(){N.current&&(H({type:"openDialog"}),N.current.value=null,N.current.click())}),[H]),V=function(){G&&setTimeout((function(){N.current&&(N.current.files.length||(H({type:"closeDialog"}),"function"==typeof A&&A()))}),300)};(0,t.useEffect)((function(){return window.addEventListener("focus",V,!1),function(){window.removeEventListener("focus",V,!1)}}),[N,G,A]);var W=(0,t.useCallback)((function(e){_.current&&_.current.isEqualNode(e.target)&&(32!==e.keyCode&&13!==e.keyCode||(e.preventDefault(),Q()))}),[_,N]),X=(0,t.useCallback)((function(){H({type:"focus"})}),[]),Y=(0,t.useCallback)((function(){H({type:"blur"})}),[]),ee=(0,t.useCallback)((function(){k||(j()?setTimeout(Q,0):Q())}),[N,k]),ne=(0,t.useRef)([]),re=function(e){_.current&&_.current.contains(e.target)||(e.preventDefault(),ne.current=[])};(0,t.useEffect)((function(){return C&&(document.addEventListener("dragover",F,!1),document.addEventListener("drop",re,!1)),function(){C&&(document.removeEventListener("dragover",F),document.removeEventListener("drop",re))}}),[_,C]);var te=(0,t.useCallback)((function(e){e.preventDefault(),e.persist(),se(e),ne.current=[].concat(E(ne.current),[e.target]),h(e)&&Promise.resolve(i(e)).then((function(n){D(e)&&!K||(H({draggedFiles:n,isDragActive:!0,type:"setDraggedFiles"}),s&&s(e))}))}),[i,s,K]),oe=(0,t.useCallback)((function(e){if(e.preventDefault(),e.persist(),se(e),e.dataTransfer)try{e.dataTransfer.dropEffect="copy"}catch(e){}return h(e)&&p&&p(e),!1}),[p,K]),ie=(0,t.useCallback)((function(e){e.preventDefault(),e.persist(),se(e);var n=ne.current.filter((function(e){return _.current&&_.current.contains(e)})),r=n.indexOf(e.target);-1!==r&&n.splice(r,1),ne.current=n,n.length>0||(H({isDragActive:!1,type:"setDraggedFiles",draggedFiles:[]}),h(e)&&f&&f(e))}),[_,f,K]),ae=(0,t.useCallback)((function(e){e.preventDefault(),e.persist(),se(e),ne.current=[],h(e)&&Promise.resolve(i(e)).then((function(n){if(!D(e)||K){var t=[],o=[];n.forEach((function(e){var n=S(v(e,r),2),i=n[0],c=n[1],l=S(y(e,u,a),2),s=l[0],f=l[1],p=M?M(e):null;if(i&&s&&!p)t.push(e);else{var g=[c,f];p&&(g=g.concat(p)),o.push({file:e,errors:g.filter((function(e){return e}))})}})),(!c&&t.length>1||c&&l>=1&&t.length>l)&&(t.forEach((function(e){o.push({file:e,errors:[d]})})),t.splice(0)),H({acceptedFiles:t,fileRejections:o,type:"setFiles"}),g&&g(t,o,e),o.length>0&&O&&O(o,e),t.length>0&&b&&b(t,e)}})),H({type:"reset"})}),[c,r,u,a,l,i,g,b,O,K]),ue=function(e){return o?null:e},ce=function(e){return x?null:ue(e)},le=function(e){return I?null:ue(e)},se=function(e){K&&e.stopPropagation()},fe=(0,t.useMemo)((function(){return function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},n=e.refKey,r=void 0===n?"ref":n,t=e.onKeyDown,i=e.onFocus,a=e.onBlur,u=e.onClick,c=e.onDragEnter,l=e.onDragOver,s=e.onDragLeave,f=e.onDrop,p=R(e,["refKey","onKeyDown","onFocus","onBlur","onClick","onDragEnter","onDragOver","onDragLeave","onDrop"]);return P(P(z({onKeyDown:ce(w(t,W)),onFocus:ce(w(i,X)),onBlur:ce(w(a,Y)),onClick:ue(w(u,ee)),onDragEnter:le(w(c,te)),onDragOver:le(w(l,oe)),onDragLeave:le(w(s,ie)),onDrop:le(w(f,ae))},r,_),o||x?{}:{tabIndex:0}),p)}}),[_,W,X,Y,ee,te,oe,ie,ae,x,I,o]),pe=(0,t.useCallback)((function(e){e.stopPropagation()}),[]),ge=(0,t.useMemo)((function(){return function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},n=e.refKey,t=void 0===n?"ref":n,o=e.onChange,i=e.onClick,a=R(e,["refKey","onChange","onClick"]),u=z({accept:r,multiple:c,type:"file",style:{display:"none"},onChange:ue(w(o,ae)),onClick:ue(w(i,pe)),autoComplete:"off",tabIndex:-1},t,N);return P(P({},u),a)}}),[N,r,c,ae,o]),de=J.length,ve=de>0&&m({files:J,accept:r,minSize:u,maxSize:a,multiple:c,maxFiles:l}),ye=de>0&&!ve;return P(P({},q),{},{isDragAccept:ve,isDragReject:ye,isFocused:Z&&!o,getRootProps:fe,getInputProps:ge,rootRef:_,inputRef:N,open:ue(Q)})}function B(e,n){switch(n.type){case"focus":return P(P({},e),{},{isFocused:!0});case"blur":return P(P({},e),{},{isFocused:!1});case"openDialog":return P(P({},e),{},{isFileDialogActive:!0});case"closeDialog":return P(P({},e),{},{isFileDialogActive:!1});case"setDraggedFiles":var r=n.isDragActive,t=n.draggedFiles;return P(P({},e),{},{draggedFiles:t,isDragActive:r});case"setFiles":return P(P({},e),{},{acceptedFiles:n.acceptedFiles,fileRejections:n.fileRejections});case"reset":return P(P({},e),{},{isFileDialogActive:!1,isDragActive:!1,draggedFiles:[],acceptedFiles:[],fileRejections:[]});default:return e}}}}]);