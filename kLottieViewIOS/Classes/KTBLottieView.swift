
import Foundation
import Lottie

@objc public class KTBLottieView: UIView {
    
    // MARK: - Private Properties
    private var playTask: DispatchWorkItem?
    private var animView: LottieAnimationView? = nil
    private var curSrc: String? = nil
    private var needAutoPlay: Bool = true
    private var needLoop: Bool = true
    
    // MARK: - UIView Overrides
    public override func layoutSubviews() {
        super.layoutSubviews()
        animView?.frame = bounds
    }
}

// MARK: - Public API (Objective-C Bridge)
public extension KTBLottieView {
    
    @objc func setSrc(path: String) {
        if path.isEmpty {
            animView?.removeFromSuperview()
            return
        } else if curSrc == path {
            return
        }
        curSrc = path
        playTask?.cancel()
        playTask = DispatchWorkItem { [weak self] in
            self?.schedule()
        }
        DispatchQueue.main.asyncAfter(deadline: .now(), execute: playTask!)
    }
    
    @objc func setLoop(value: Bool) {
        print("LottieView setLoop ",value)
        needLoop = value
    }
    
    @objc func setAutoPlay(value: Bool) {
        print("LottieView setAutoPlay ",value)
        needAutoPlay = value
    }
    
    @objc func play() {
        animView?.stop()
        animView?.play()
    }
    
    @objc func pause() {
        animView?.pause()
    }
    
    @objc func resume() {
        animView?.play()
    }
    
    @objc func progress(value: NSDictionary) {
        let ratio = value.value(forKey: "param") as? String ?? "0"
        animView?.stop()
        animView?.currentProgress = AnimationProgressTime(Float(ratio) ?? 0)
    }
}

// MARK: - Private Methods
private extension KTBLottieView {
    
    func schedule() {
        print("LottieView schedule...",needLoop,needAutoPlay)
        animView?.removeFromSuperview()
        let src = curSrc ?? ""
        let _animView = LottieAnimationView(name: String(src.dropLast(".json".count)))
        addSubview(_animView)
        _animView.loopMode = needLoop ? .loop : .playOnce
        if needAutoPlay {
            _animView.play()
        }
        _animView.frame = bounds
        animView = _animView
    }
}
