(self.webpackChunk_unified_activity=self.webpackChunk_unified_activity||[]).push([[7468],{52410:(t,e,n)=>{"use strict";n.d(e,{Z:()=>f});var r=n(54903),i=n(12606),o=n(73141),s=(n(13980),n(74042)),a=n(94693),u=n(12196),l=n.n(u),p=n(14657),c=function(t,e){return t&&e&&e.split(" ").forEach((function(e){return(0,a.Z)(t,e)}))},d=function(t){function e(){for(var e,n=arguments.length,r=new Array(n),i=0;i<n;i++)r[i]=arguments[i];return(e=t.call.apply(t,[this].concat(r))||this).appliedClasses={appear:{},enter:{},exit:{}},e.onEnter=function(t,n){var r=e.resolveArguments(t,n),i=r[0],o=r[1];e.removeClasses(i,"exit"),e.addClass(i,o?"appear":"enter","base"),e.props.onEnter&&e.props.onEnter(t,n)},e.onEntering=function(t,n){var r=e.resolveArguments(t,n),i=r[0],o=r[1]?"appear":"enter";e.addClass(i,o,"active"),e.props.onEntering&&e.props.onEntering(t,n)},e.onEntered=function(t,n){var r=e.resolveArguments(t,n),i=r[0],o=r[1]?"appear":"enter";e.removeClasses(i,o),e.addClass(i,o,"done"),e.props.onEntered&&e.props.onEntered(t,n)},e.onExit=function(t){var n=e.resolveArguments(t)[0];e.removeClasses(n,"appear"),e.removeClasses(n,"enter"),e.addClass(n,"exit","base"),e.props.onExit&&e.props.onExit(t)},e.onExiting=function(t){var n=e.resolveArguments(t)[0];e.addClass(n,"exit","active"),e.props.onExiting&&e.props.onExiting(t)},e.onExited=function(t){var n=e.resolveArguments(t)[0];e.removeClasses(n,"exit"),e.addClass(n,"exit","done"),e.props.onExited&&e.props.onExited(t)},e.resolveArguments=function(t,n){return e.props.nodeRef?[e.props.nodeRef.current,t]:[t,n]},e.getClassNames=function(t){var n=e.props.classNames,r="string"==typeof n,i=r?(r&&n?n+"-":"")+t:n[t];return{baseClassName:i,activeClassName:r?i+"-active":n[t+"Active"],doneClassName:r?i+"-done":n[t+"Done"]}},e}(0,o.Z)(e,t);var n=e.prototype;return n.addClass=function(t,e,n){var r=this.getClassNames(e)[n+"ClassName"],i=this.getClassNames("enter").doneClassName;"appear"===e&&"done"===n&&i&&(r+=" "+i),"active"===n&&t&&t.scrollTop,r&&(this.appliedClasses[e][n]=r,function(t,e){t&&e&&e.split(" ").forEach((function(e){return(0,s.Z)(t,e)}))}(t,r))},n.removeClasses=function(t,e){var n=this.appliedClasses[e],r=n.base,i=n.active,o=n.done;this.appliedClasses[e]={},r&&c(t,r),i&&c(t,i),o&&c(t,o)},n.render=function(){var t=this.props,e=(t.classNames,(0,i.Z)(t,["classNames"]));return l().createElement(p.ZP,(0,r.Z)({},e,{onEnter:this.onEnter,onEntered:this.onEntered,onEntering:this.onEntering,onExit:this.onExit,onExiting:this.onExiting,onExited:this.onExited}))},e}(l().Component);d.defaultProps={classNames:""},d.propTypes={};const f=d},14657:(t,e,n)=>{"use strict";n.d(e,{ZP:()=>v});var r=n(12606),i=n(73141),o=(n(13980),n(12196)),s=n.n(o),a=n(54489),u=n.n(a);var l=n(77008),p="unmounted",c="exited",d="entering",f="entered",h="exiting",E=function(t){function e(e,n){var r;r=t.call(this,e,n)||this;var i,o=n&&!n.isMounting?e.enter:e.appear;return r.appearStatus=null,e.in?o?(i=c,r.appearStatus=d):i=f:i=e.unmountOnExit||e.mountOnEnter?p:c,r.state={status:i},r.nextCallback=null,r}(0,i.Z)(e,t),e.getDerivedStateFromProps=function(t,e){return t.in&&e.status===p?{status:c}:null};var n=e.prototype;return n.componentDidMount=function(){this.updateStatus(!0,this.appearStatus)},n.componentDidUpdate=function(t){var e=null;if(t!==this.props){var n=this.state.status;this.props.in?n!==d&&n!==f&&(e=d):n!==d&&n!==f||(e=h)}this.updateStatus(!1,e)},n.componentWillUnmount=function(){this.cancelNextCallback()},n.getTimeouts=function(){var t,e,n,r=this.props.timeout;return t=e=n=r,null!=r&&"number"!=typeof r&&(t=r.exit,e=r.enter,n=void 0!==r.appear?r.appear:e),{exit:t,enter:e,appear:n}},n.updateStatus=function(t,e){void 0===t&&(t=!1),null!==e?(this.cancelNextCallback(),e===d?this.performEnter(t):this.performExit()):this.props.unmountOnExit&&this.state.status===c&&this.setState({status:p})},n.performEnter=function(t){var e=this,n=this.props.enter,r=this.context?this.context.isMounting:t,i=this.props.nodeRef?[r]:[u().findDOMNode(this),r],o=i[0],s=i[1],a=this.getTimeouts(),l=r?a.appear:a.enter;t||n?(this.props.onEnter(o,s),this.safeSetState({status:d},(function(){e.props.onEntering(o,s),e.onTransitionEnd(l,(function(){e.safeSetState({status:f},(function(){e.props.onEntered(o,s)}))}))}))):this.safeSetState({status:f},(function(){e.props.onEntered(o)}))},n.performExit=function(){var t=this,e=this.props.exit,n=this.getTimeouts(),r=this.props.nodeRef?void 0:u().findDOMNode(this);e?(this.props.onExit(r),this.safeSetState({status:h},(function(){t.props.onExiting(r),t.onTransitionEnd(n.exit,(function(){t.safeSetState({status:c},(function(){t.props.onExited(r)}))}))}))):this.safeSetState({status:c},(function(){t.props.onExited(r)}))},n.cancelNextCallback=function(){null!==this.nextCallback&&(this.nextCallback.cancel(),this.nextCallback=null)},n.safeSetState=function(t,e){e=this.setNextCallback(e),this.setState(t,e)},n.setNextCallback=function(t){var e=this,n=!0;return this.nextCallback=function(r){n&&(n=!1,e.nextCallback=null,t(r))},this.nextCallback.cancel=function(){n=!1},this.nextCallback},n.onTransitionEnd=function(t,e){this.setNextCallback(e);var n=this.props.nodeRef?this.props.nodeRef.current:u().findDOMNode(this),r=null==t&&!this.props.addEndListener;if(n&&!r){if(this.props.addEndListener){var i=this.props.nodeRef?[this.nextCallback]:[n,this.nextCallback],o=i[0],s=i[1];this.props.addEndListener(o,s)}null!=t&&setTimeout(this.nextCallback,t)}else setTimeout(this.nextCallback,0)},n.render=function(){var t=this.state.status;if(t===p)return null;var e=this.props,n=e.children,i=(e.in,e.mountOnEnter,e.unmountOnExit,e.appear,e.enter,e.exit,e.timeout,e.addEndListener,e.onEnter,e.onEntering,e.onEntered,e.onExit,e.onExiting,e.onExited,e.nodeRef,(0,r.Z)(e,["children","in","mountOnEnter","unmountOnExit","appear","enter","exit","timeout","addEndListener","onEnter","onEntering","onEntered","onExit","onExiting","onExited","nodeRef"]));return s().createElement(l.Z.Provider,{value:null},"function"==typeof n?n(t,i):s().cloneElement(s().Children.only(n),i))},e}(s().Component);function x(){}E.contextType=l.Z,E.propTypes={},E.defaultProps={in:!1,mountOnEnter:!1,unmountOnExit:!1,appear:!1,enter:!0,exit:!0,onEnter:x,onEntering:x,onEntered:x,onExit:x,onExiting:x,onExited:x},E.UNMOUNTED=p,E.EXITED=c,E.ENTERING=d,E.ENTERED=f,E.EXITING=h;const v=E},11121:(t,e,n)=>{"use strict";n.d(e,{Z:()=>h});var r=n(12606),i=n(54903),o=n(73141),s=(n(13980),n(12196)),a=n.n(s),u=n(77008);function l(t,e){var n=Object.create(null);return t&&s.Children.map(t,(function(t){return t})).forEach((function(t){n[t.key]=function(t){return e&&(0,s.isValidElement)(t)?e(t):t}(t)})),n}function p(t,e,n){return null!=n[e]?n[e]:t.props[e]}function c(t,e,n){var r=l(t.children),i=function(t,e){function n(n){return n in e?e[n]:t[n]}t=t||{},e=e||{};var r,i=Object.create(null),o=[];for(var s in t)s in e?o.length&&(i[s]=o,o=[]):o.push(s);var a={};for(var u in e){if(i[u])for(r=0;r<i[u].length;r++){var l=i[u][r];a[i[u][r]]=n(l)}a[u]=n(u)}for(r=0;r<o.length;r++)a[o[r]]=n(o[r]);return a}(e,r);return Object.keys(i).forEach((function(o){var a=i[o];if((0,s.isValidElement)(a)){var u=o in e,l=o in r,c=e[o],d=(0,s.isValidElement)(c)&&!c.props.in;!l||u&&!d?l||!u||d?l&&u&&(0,s.isValidElement)(c)&&(i[o]=(0,s.cloneElement)(a,{onExited:n.bind(null,a),in:c.props.in,exit:p(a,"exit",t),enter:p(a,"enter",t)})):i[o]=(0,s.cloneElement)(a,{in:!1}):i[o]=(0,s.cloneElement)(a,{onExited:n.bind(null,a),in:!0,exit:p(a,"exit",t),enter:p(a,"enter",t)})}})),i}var d=Object.values||function(t){return Object.keys(t).map((function(e){return t[e]}))},f=function(t){function e(e,n){var r,i=(r=t.call(this,e,n)||this).handleExited.bind(function(t){if(void 0===t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return t}(r));return r.state={contextValue:{isMounting:!0},handleExited:i,firstRender:!0},r}(0,o.Z)(e,t);var n=e.prototype;return n.componentDidMount=function(){this.mounted=!0,this.setState({contextValue:{isMounting:!1}})},n.componentWillUnmount=function(){this.mounted=!1},e.getDerivedStateFromProps=function(t,e){var n,r,i=e.children,o=e.handleExited;return{children:e.firstRender?(n=t,r=o,l(n.children,(function(t){return(0,s.cloneElement)(t,{onExited:r.bind(null,t),in:!0,appear:p(t,"appear",n),enter:p(t,"enter",n),exit:p(t,"exit",n)})}))):c(t,i,o),firstRender:!1}},n.handleExited=function(t,e){var n=l(this.props.children);t.key in n||(t.props.onExited&&t.props.onExited(e),this.mounted&&this.setState((function(e){var n=(0,i.Z)({},e.children);return delete n[t.key],{children:n}})))},n.render=function(){var t=this.props,e=t.component,n=t.childFactory,i=(0,r.Z)(t,["component","childFactory"]),o=this.state.contextValue,s=d(this.state.children).map(n);return delete i.appear,delete i.enter,delete i.exit,null===e?a().createElement(u.Z.Provider,{value:o},s):a().createElement(u.Z.Provider,{value:o},a().createElement(e,i,s))},e}(a().Component);f.propTypes={},f.defaultProps={component:"div",childFactory:function(t){return t}};const h=f},77008:(t,e,n)=>{"use strict";n.d(e,{Z:()=>i});var r=n(12196);const i=n.n(r)().createContext(null)},54903:(t,e,n)=>{"use strict";function r(){return(r=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t}).apply(this,arguments)}n.d(e,{Z:()=>r})},73141:(t,e,n)=>{"use strict";function r(t,e){return(r=Object.setPrototypeOf||function(t,e){return t.__proto__=e,t})(t,e)}function i(t,e){t.prototype=Object.create(e.prototype),t.prototype.constructor=t,r(t,e)}n.d(e,{Z:()=>i})},12606:(t,e,n)=>{"use strict";function r(t,e){if(null==t)return{};var n,r,i={},o=Object.keys(t);for(r=0;r<o.length;r++)n=o[r],e.indexOf(n)>=0||(i[n]=t[n]);return i}n.d(e,{Z:()=>r})}}]);