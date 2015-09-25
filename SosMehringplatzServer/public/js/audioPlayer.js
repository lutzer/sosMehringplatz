
var AudioPlayer = function(file,endListener) {

    this.audio = new Audio(file);
    this.audio.play();
    this.listener = endListener;
    this.audio.addEventListener('ended', endListener);
};

AudioPlayer.prototype.stop = function() {
	if (this.audio) {
	    this.audio.pause();
	    this.audio.removeEventListener(this.listener);
	    this.audio = null
	}
};
