/** Shared keyboard and label behavior for dynamically rendered forms/dialogs. */
export function initializeAccessibility(root=document) {
  let sequence=0;
  let activeDialog=null;
  let returnFocus=null;
  const visible=node=>node.isConnected&&!node.hidden&&node.getClientRects().length>0;
  const controls=dialog=>[...dialog.querySelectorAll('button:not([disabled]),a[href],input:not([disabled]):not([type="hidden"]),textarea:not([disabled]),select:not([disabled]),[tabindex="0"]')].filter(visible);
  function synchronize(){
    root.querySelectorAll('.field').forEach(field=>{
      const label=field.querySelector('label'),input=field.querySelector('input,select,textarea');
      if(!input||!label||label.contains(input)||label.htmlFor||input.getAttribute('aria-label'))return;
      if(!input.id)input.id=`accessible-field-${++sequence}`;
      label.htmlFor=input.id;
    });
    const dialogs=[...root.querySelectorAll('.modal-backdrop .modal,.drawer-backdrop .drawer,.about-modal-backdrop:not([hidden]) .about-modal')].filter(visible);
    const next=dialogs.at(-1)||null;
    root.body.classList.toggle('dialog-open',Boolean(next));
    if(next){
      next.setAttribute('role','dialog');next.setAttribute('aria-modal','true');
      const heading=next.querySelector('h1,h2,h3');
      if(heading){if(!heading.id)heading.id=`dialog-title-${++sequence}`;next.setAttribute('aria-labelledby',heading.id);}
    }
    if(next===activeDialog)return;
    if(next){
      if(!activeDialog)returnFocus=root.activeElement;
      activeDialog=next;
      (controls(next)[0]||next).focus({preventScroll:true});
    }else{activeDialog=null;if(returnFocus?.isConnected)returnFocus.focus({preventScroll:true});returnFocus=null;}
  }
  root.addEventListener('keydown',event=>{
    if(!activeDialog||!visible(activeDialog))return;
    if(event.key==='Escape'){
      const close=activeDialog.querySelector('[data-close-modal],[data-close-cart],[data-close-support],[data-close-admin-edit],[data-close-catalog-edit],.close-button');
      if(close){event.preventDefault();close.click();}return;
    }
    if(event.key!=='Tab')return;
    const items=controls(activeDialog);if(!items.length){event.preventDefault();return;}
    const first=items[0],last=items.at(-1);
    if(event.shiftKey&&(root.activeElement===first||!activeDialog.contains(root.activeElement))){event.preventDefault();last.focus();}
    else if(!event.shiftKey&&(root.activeElement===last||!activeDialog.contains(root.activeElement))){event.preventDefault();first.focus();}
  });
  const observer=new MutationObserver(synchronize);observer.observe(root.body,{childList:true,subtree:true,attributes:true,attributeFilter:['hidden']});
  synchronize();
  return ()=>observer.disconnect();
}
