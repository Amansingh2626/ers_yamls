(self.webpackChunk_unified_bulk=self.webpackChunk_unified_bulk||[]).push([[1858],{2897:(e,t,n)=>{e.exports={parse:n(1944),stringify:n(984)}},698:(e,t,n)=>{var r=/([\w-]+)|=|(['"])([.\s\S]*?)\2/g,i=n(4896);e.exports=function(e){var t,n=0,c=!0,a={type:"tag",name:"",voidElement:!1,attrs:{},children:[]};return e.replace(r,(function(r){if("="===r)return c=!0,void n++;c?0===n?((i[r]||"/"===e.charAt(e.length-2))&&(a.voidElement=!0),a.name=r):(a.attrs[t]=r.replace(/^['"]|['"]$/g,""),t=void 0):(t&&(a.attrs[t]=t),t=r),n++,c=!1})),a}},1944:(e,t,n)=>{var r=/(?:<!--[\S\s]*?-->|<(?:"[^"]*"['"]*|'[^']*'['"]*|[^'">])+>)/g,i=n(698),c=Object.create?Object.create(null):{};function a(e,t,n,r,i){var c=t.indexOf("<",r),a=t.slice(r,-1===c?void 0:c);/^\s*$/.test(a)&&(a=" "),(!i&&c>-1&&n+e.length>=0||" "!==a)&&e.push({type:"text",content:a})}e.exports=function(e,t){t||(t={}),t.components||(t.components=c);var n,o=[],s=-1,u=[],l={},p=!1;return e.replace(r,(function(r,c){if(p){if(r!=="</"+n.name+">")return;p=!1}var h,d="/"!==r.charAt(1),f=0===r.indexOf("\x3c!--"),g=c+r.length,m=e.charAt(g);d&&!f&&(s++,"tag"===(n=i(r)).type&&t.components[n.name]&&(n.type="component",p=!0),n.voidElement||p||!m||"<"===m||a(n.children,e,s,g,t.ignoreWhitespace),l[n.tagName]=n,0===s&&o.push(n),(h=u[s-1])&&h.children.push(n),u[s]=n),(f||!d||n.voidElement)&&(f||s--,!p&&"<"!==m&&m&&a(h=-1===s?o:u[s].children,e,s,g,t.ignoreWhitespace))})),!o.length&&e.length&&a(o,e,0,0,t.ignoreWhitespace),o}},984:e=>{function t(e,n){switch(n.type){case"text":return e+n.content;case"tag":return e+="<"+n.name+(n.attrs?function(e){var t=[];for(var n in e)t.push(n+'="'+e[n]+'"');return t.length?" "+t.join(" "):""}(n.attrs):"")+(n.voidElement?"/>":">"),n.voidElement?e:e+n.children.reduce(t,"")+"</"+n.name+">"}}e.exports=function(e){return e.reduce((function(e,n){return e+t("",n)}),"")}}}]);