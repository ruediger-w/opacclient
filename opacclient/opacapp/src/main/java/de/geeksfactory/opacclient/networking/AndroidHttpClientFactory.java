/**
 * Copyright (C) 2013 by Raphael Michel under the MIT license:
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.geeksfactory.opacclient.networking;

import android.os.Build;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import de.geeksfactory.opacclient.OpacClient;
import de.geeksfactory.opacclient.utils.DebugTools;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class AndroidHttpClientFactory extends HttpClientFactory {

    public AndroidHttpClientFactory() {
        super("OpacApp/" + OpacClient.versionName);
    }

    @Override
    public KeyStore getKeyStore()
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        return super.getKeyStore();
    }

    @Override
    protected Class<?> getSocketFactoryClass(boolean tls_only, boolean allCipherSuites) {
        if (tls_only) {
            if (allCipherSuites) {
                return TlsSniSocketFactoryWithAllCipherSuites.class;
            } else {
                return TlsSniSocketFactory.class;
            }
        } else {
            return TlsSniSocketFactoryWithSSL3.class;
        }
    }

    @Override
    public OkHttpClient getNewOkHttpClient(boolean customssl, boolean tls_only,
                                           boolean allCipherSuites) {
        OkHttpClient.Builder client =
                super.getOkHttpClientBuilder(customssl, tls_only, allCipherSuites,
                        Build.VERSION.SDK_INT == 24);
        int cacheSize = 50 * 1024 * 1024; // 50MB
        client.cache(new Cache(OpacClient.context.getCacheDir(), cacheSize));
        return DebugTools.prepareHttpClient(client).build();
    }
}