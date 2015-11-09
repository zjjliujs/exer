// Help choose from four UI pseudo-window flavors
function getCurrOSUI( ) {
  var ua = navigator.userAgent;
  if (ua.indexOf("Mac") != -1) {
    if (ua.indexOf("OS X") != -1 || ua.indexOf("MSIE 5.2") != -1) {
      return "macosX";
    } else {
      return "macos9";
    }
  } else if (ua.indexOf("Windows XP") != -1 || ua.indexOf("NT 5.1") != -1) {
    return "winxp";
  } else if ((document.compatMode && document.compatMode != "BackComp")
  ||(navigator.product && navigator.product == "Gecko")) {
    // Win9x and CSS-compatible
    return "win9x";
  } else {
    // default for Windows 9x in quirks mode, Unix/Linux, & unknowns
    return "win9xQ";
  }
}
var currOS = getCurrOSUI( );
// Load OS-specific style sheet for pseudo dialog layer
document.write("<link rel='stylesheet' type='text/css' href='../css/layer_dialog_" + currOS +
".css'>");

//******************************
//  Begin Layer Dialog Code
//******************************/

// One object tracks the current pseudo-window layer.
var dialogLayer = {layer:null, visible:false};

// Center a positionable element whose name is passed as
// a parameter in the current window/frame, and show it
function centerOnWindow(elemID) {
  // 'obj' is the positionable object
  var obj = getRawObject(elemID);
  // window scroll factors
  var scrollX = 0, scrollY = 0;
  if (document.body && typeof document.body.scrollTop != "undefined") {
    scrollX += document.body.scrollLeft;
    scrollY += document.body.scrollTop;
    if (document.body.parentNode &&
      typeof document.body.parentNode.scrollTop != "undefined") {
        scrollX += document.body.parentNode.scrollLeft;
        scrollY += document.body.parentNode.scrollTop
      }
    } else if (typeof window.pageXOffset != "undefined") {
      scrollX += window.pageXOffset;
      scrollY += window.pageYOffset;
    }
    var x = Math.round((getInsideWindowWidth( )/2) - (getObjectWidth(obj)/2)) + scrollX;
    var y = Math.round((getInsideWindowHeight( )/2) - (getObjectHeight(obj)/2)) + scrollY;
    shiftTo(obj, x, y);
  }

  function initLayerDialog( ) {
    document.getElementById("closebox").src="../images/close_box_" + currOS + ".jpg";
    dialogLayer.layer = document.getElementById("pseudoWindow");
  }

  // Set up and display pseudo-window.
  // Parameters:
  //    url -- URL of the page/frameset to be loaded into iframe
  //    returnFunc -- reference to the function (on this page)
  //                  that is to act on the data returned from the dialog
  //    args -- [optional] any data you need to pass to the dialog
  function openLayerDialog(url, title, returnFunc, args) {
    if (!dialogLayer.visible) {
      // Initialize properties of the modal dialog object.
      dialogLayer.url = url;
      dialogLayer.title = title;
      dialogLayer.returnFunc = returnFunc;
      dialogLayer.args = args;
      dialogLayer.returnedValue = "";

      // Load URL
      document.getElementById("contentFrame").src = url;

      // Set title of "window"
      document.getElementById("barTitle").firstChild.nodeValue = title;

      // Center "window" in browser window or frame
      dialogLayer.layer.style.visibility = "hidden";
      dialogLayer.layer.style.display = "block"
      centerOnWindow("pseudoWindow");

      // Show it and set visibility flag
      dialogLayer.layer.style.visibility = "visible"
      dialogLayer.visible = true;
    }
  }

  function closeLayerDialog( ) {
    dialogLayer.layer.style.display = "none"
    dialogLayer.visible = false;
  }
  //**************************
  //  End Layer Dialog Code
  //**************************/
