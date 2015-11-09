// Global holds reference to selected element
var selectedObj;
// Globals hold location of click relative to element
var offsetX, offsetY;

// Set global reference to element being engaged and dragged
function setSelectedElem(evt) {
    var target = (evt.target) ? evt.target : evt.srcElement;
    var divID = (target.id == "titlebar") ? "pseudoWindow" : "";
    if (divID) {
        if (document.layers) {
            selectedObj = document.layers[divID];
        } else if (document.all) {
            selectedObj = document.all(divID);
        } else if (document.getElementById) {
            selectedObj = document.getElementById(divID);
        }
        setZIndex(selectedObj, 100);
        return;
    }
    selectedObj = null;
    return;
}

// Turn selected element on
function engage(evt) {
    evt = (evt) ? evt : event;
    setSelectedElem(evt);
    if (selectedObj) {
       if (document.body && document.body.setCapture) {
          // engage event capture in IE/Win
          document.body.setCapture();
       }
        if (evt.pageX) {
            offsetX = evt.pageX - ((typeof selectedObj.offsetLeft != "undefined") ?
                      selectedObj.offsetLeft : selectedObj.left);
            offsetY = evt.pageY - ((selectedObj.offsetTop) ?
                      selectedObj.offsetTop : selectedObj.top);
        } else if (typeof evt.clientX != "undefined") {
            offsetX = evt.clientX - ((selectedObj.offsetLeft) ?
                      selectedObj.offsetLeft : 0);
            offsetY = evt.clientY - ((selectedObj.offsetTop) ?
                      selectedObj.offsetTop : 0);
        } else if (evt.offsetX || evt/offsetY) {
            offsetX = evt.offsetX - ((evt.offsetX < -2) ?
                      0 : document.body.scrollLeft);
            offsetX -= (document.body.parentElement &&
                     document.body.parentElement.scrollLeft) ?
                     document.body.parentElement.scrollLeft : 0
            offsetY = evt.offsetY - ((evt.offsetY < -2) ?
                      0 : document.body.scrollTop);
            offsetY -= (document.body.parentElement &&
                     document.body.parentElement.scrollTop) ?
                     document.body.parentElement.scrollTop : 0
        }
        evt.cancelBubble = true;
        return false;
    }
}

// Drag an element
function dragIt(evt) {
    evt = (evt) ? evt : event;
    if (selectedObj) {
        if (evt.pageX) {
            shiftTo(selectedObj, (evt.pageX - offsetX), (evt.pageY - offsetY));
        } else if (evt.clientX || evt.clientY) {
            shiftTo(selectedObj, (evt.clientX - offsetX), (evt.clientY - offsetY));
        }
        evt.cancelBubble = true;
        return false;
    }
}

// Turn selected element off
function release(evt) {
    if (selectedObj) {
       setZIndex(selectedObj, 0)
       if (document.body && document.body.releaseCapture) {
          // stop event capture in IE/Win
          document.body.releaseCapture();
        }
        selectedObj = null;
    }
}

function blockEvents(evt) {
    evt = (evt) ? evt : event;
    evt.cancelBubble = true;
    return false;
}

// Assign event handlers used by both Navigator and IE
function initDrag( ) {
    if (document.layers) {
        // turn on event capture for these events in NN4 event model
        document.captureEvents(Event.MOUSEDOWN | Event.MOUSEMOVE | Event.MOUSEUP);
        return;
    } else if (document.body & document.body.addEventListener) {
        // turn on event capture for these events in W3C DOM event model
        document.addEventListener("mousedown", engage, true);
        document.addEventListener("mousemove", dragIt, true);
        document.addEventListener("mouseup", release, true);
        return;
    }
    document.onmousedown = engage;
    document.onmousemove = dragIt;
    document.onmouseup = release;
    return;
}
