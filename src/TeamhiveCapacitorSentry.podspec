
  Pod::Spec.new do |s|
    s.name = 'TeamhiveCapacitorSentry'
    s.version = '0.0.1'
    s.summary = 'Capacitor sentry sdk'
    s.license = 'MIT'
    s.homepage = 'https://github.com/TeamHive/capacitor-sentry'
    s.author = 'TeamHive'
    s.source = { :git => 'https://github.com/TeamHive/capacitor-sentry', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '10.0'
    s.dependency 'Capacitor'
    s.dependency  'Sentry', '~> 4.1.0'
  end
