/*! For license information please see unified.react-router.857ef799.js.LICENSE.txt */
(self.webpackChunk_unified_config_vodafone=self.webpackChunk_unified_config_vodafone||[]).push([[486],{149:(t,o,e)=>{"use strict";function r(t,o){return(r=Object.setPrototypeOf||function(t,o){return t.__proto__=o,t})(t,o)}e.d(o,{F0:()=>f,s6:()=>l,LX:()=>y});var n=e(172),i=e.n(n),a=(e(980),e(585),e(53));e(898);var c=e(99),s=e.n(c);e(744),e(463);var u=function(t){var o=(0,a.Z)();return o.displayName="Router-History",o}(),l=function(t){var o=(0,a.Z)();return o.displayName="Router",o}(),f=function(t){function o(o){var e;return(e=t.call(this,o)||this).state={location:o.history.location},e._isMounted=!1,e._pendingLocation=null,o.staticContext||(e.unlisten=o.history.listen((function(t){e._isMounted?e.setState({location:t}):e._pendingLocation=t}))),e}var e,n;n=t,(e=o).prototype=Object.create(n.prototype),e.prototype.constructor=e,r(e,n),o.computeRootMatch=function(t){return{path:"/",url:"/",params:{},isExact:"/"===t}};var a=o.prototype;return a.componentDidMount=function(){this._isMounted=!0,this._pendingLocation&&this.setState({location:this._pendingLocation})},a.componentWillUnmount=function(){this.unlisten&&this.unlisten()},a.render=function(){return i().createElement(l.Provider,{value:{history:this.props.history,location:this.state.location,match:o.computeRootMatch(this.state.location.pathname),staticContext:this.props.staticContext}},i().createElement(u.Provider,{children:this.props.children||null,value:this.props.history}))},o}(i().Component);i().Component,i().Component;var p={},m=0;function y(t,o){void 0===o&&(o={}),("string"==typeof o||Array.isArray(o))&&(o={path:o});var e=o,r=e.path,n=e.exact,i=void 0!==n&&n,a=e.strict,c=void 0!==a&&a,u=e.sensitive,l=void 0!==u&&u;return[].concat(r).reduce((function(o,e){if(!e&&""!==e)return null;if(o)return o;var r=function(t,o){var e=""+o.end+o.strict+o.sensitive,r=p[e]||(p[e]={});if(r[t])return r[t];var n=[],i={regexp:s()(t,n,o),keys:n};return m<1e4&&(r[t]=i,m++),i}(e,{end:i,strict:c,sensitive:l}),n=r.regexp,a=r.keys,u=n.exec(t);if(!u)return null;var f=u[0],y=u.slice(1),d=t===f;return i&&!d?null:{path:e,url:"/"===e&&""===f?"/":f,isExact:d,params:a.reduce((function(t,o,e){return t[o.name]=y[e],t}),{})}}),null)}i().Component,i().Component,i().Component,i().useContext},897:(t,o)=>{"use strict";var e="function"==typeof Symbol&&Symbol.for;e&&Symbol.for("react.element"),e&&Symbol.for("react.portal"),e&&Symbol.for("react.fragment"),e&&Symbol.for("react.strict_mode"),e&&Symbol.for("react.profiler"),e&&Symbol.for("react.provider"),e&&Symbol.for("react.context"),e&&Symbol.for("react.async_mode"),e&&Symbol.for("react.concurrent_mode"),e&&Symbol.for("react.forward_ref"),e&&Symbol.for("react.suspense"),e&&Symbol.for("react.suspense_list"),e&&Symbol.for("react.memo"),e&&Symbol.for("react.lazy"),e&&Symbol.for("react.block"),e&&Symbol.for("react.fundamental"),e&&Symbol.for("react.responder"),e&&Symbol.for("react.scope")},744:(t,o,e)=>{"use strict";e(897)}}]);