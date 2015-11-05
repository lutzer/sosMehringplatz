
var AudioPlayer = function(file,endListener,errorCallback) {

    this.audio = new Audio(file);
    this.audio.play();
    this.listener = endListener;
    this.audio.addEventListener('ended', endListener);
    this.audio.onerror = function() {
    	if (errorCallback)
    		errorCallback();
    }
};

AudioPlayer.prototype.stop = function() {
	if (this.audio) {
	    this.audio.pause();
	    this.audio.removeEventListener('ended',this.listener);
	    this.audio = null
	}
};
