Pod::Spec.new do |spec|
  spec.name             = "kLottieViewIOS"
  spec.version          = "1.0.0"
  spec.summary          = "kLottieViewIOS is a iOS native component of kuiklyLottieView"
  spec.description      = <<-DESC
                        -kLottieViewIOS is a iOS native component of kuiklyLottieView
                        DESC
  spec.homepage         = "Link to the KuiklyLib_kuikly_ios Module homepage"
  spec.license          = { :type => "Apache-2.0", :file => "LICENSE" }
  spec.author           = { "walkman707" => "" }
  spec.ios.deployment_target = '12.0'
  spec.source           = { :git => "https://github.com/walkman707/KuiklyLottie", :tag => "#{spec.version}" }

  spec.pod_target_xcconfig = {
        'VALID_ARCHS[sdk=iphonesimulator*]' => 'arm64 x86_64'
  }
  spec.dependency 'OpenKuiklyIOSRender', '~> 2.4.0'
  spec.dependency 'lottie-ios', '~> 3.0'
  spec.requires_arc     = true
  spec.source_files = 'kLottieViewIOS/Classes/**/*'
  spec.static_framework = true
  spec.libraries    = "c++"
end