/*
 * GNU GENERAL PUBLIC LICENSE
 *                 Version 3, 29 June 2007
 *
 *     Copyright (c) 2015 Joaquim Ley <me@joaquimley.com>
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.joaquimley.birdsseyeview.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.joaquimley.birdsseyeview.R;
import com.joaquimley.birdsseyeview.helpers.GoogleMapAnimationHelper;
import com.joaquimley.birdsseyeview.helpers.GoogleMapHelper;
import com.joaquimley.birdsseyeview.helpers.jalur_angkot;
import com.joaquimley.birdsseyeview.utils.TestValues;
import com.joaquimley.birdsseyeview.utils.Util;

import java.io.IOException;

/**
 * Launcher activity with Google Map fragment
 */

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLoadedCallback,
        Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Marker mMarker;
    private AnimatorSet mAnimatorSet;
    private Menu mMenu;
    private LatLng[] mRouteExample;
    Spinner spiner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); //unidha fix ckl
        String list[]={"pilih jalur","gadang-unikama(ga)","arjosari-unikama(ga)","landungsari-unikama(gl)","landungsari-unisma(gl)","gadang-unisma(lg)","arjosari-unisma(al)","arjosari-uin(al)","gadang-uin(lg)","landungsari-uin(gL)","arjosari-umm(al)","gadang-umm(lg)",
        "arjosari-ub(adl)","gadang-ub(lg)","landungsari-ub(al)","arjosari-um(al)","gadang-um(gl)","landungsari-um(al)","arjosari-itn(al)","gadang-itn(lg)",
                "landungsari-itn(gl)","arjosari-polinema(adl)","landungsari-polinema(adl)","gadang-polinema(lg)","arjosari-stmik asia(abg)","gadang-stmik asia(abg)",
                "landungsari-stmik asia(adl)","landungsari-Unidha(ckl)","gadang-Unidha(ajg-ckl)","arjosari-Unidha(al-ckl)","arjosari-uwg(abg)","gadang-uwg(abg)","landungsari-uwg(ckl-abg)","landungsari-ukcw(adl)","arjosari-ukcw(adl)","gadang-ukcw(gl-adl)",
                "landungsari-univ katolik widya(lg)","arjosari-univ katolik widya(al)","gadang-univ katolik widya(lg)","landungsari-ikip budi utomo(gl)",
                "landungsari-ikip budi utomo(gl)","gadang-ikip budi utomo(ldg)","arjosari-ikip budi utomo(at)","arjosari-ipm(abg)","gadang-ipm(abg)","landungsari-ipm(ckl)",
                "landungsari-pum(gl)","gadang-pum(lg)","arjosari-pum(al)","arjosari-poltekes kemenkes(al)","landungsari-poltekes kemenkes(adl)","gadang-poltekes kemenkes(gl)",
                "gadang-poltekes soepraon(ga)","arjosari-poltekes soepraon(ga)","landungsari-poltekes soepraon(gl)","arjosari-akademi pariwisata dan hotel(adl)","landungsari-akademi pariwisata dan hotel(adl)","gadang-akademi pariwisata dan hotel(amg)","gadang-akd farmasi putra(ajg)","arjosari-akd farmasi putra(asd)","landungsari-akd farmasi putra(adl-asd)",
                "gadang-akd keperawatan waluyo(ag)","landungsari-akd keperawatan waluyo(gl)","arjosari-akd keperawatan waluyo(ga-gl)",
                "gadang-akd wira husada(lg)","landungsari-akd wira husada(gl)","arjosari-akd wira husada(al)","gadang-akd wijaya kusuma(abg)","landungsari-akd wijaya kusuma(adl)","arjosari-akd wijaya kusuma(al)","landungsari-akd analisis kesehatan(ckl)","gadang-akd analisis kesehatan(ajg-ckl)","arjosari-akd analisis kesehatan(al-ckl)",
                "arjosari-balai diklat(ag)","landungsari-balai diklat(adl)","gadang-balai diklat(abg)","landungsari-sekolah atlas nusantara(adl)","gadang-sekolah atlas nusantara(abg)","landungsari-sekolah tinggi teknik(ckl)","arjosari-sekolah tinggi teknik(abg)","gadang-sekolah tinggi teknik(abg)","landungsari-sekolah tinggi bahasa asing(ckl)","gadang-sekolah tinggi bahasa asing(ajg-ckl)","arjosari-sekolah tinggi bahasa asing(al-ckl)",
                "arjosari-sekolah tinggi filsafat teologi(at)","landungsari-sekolah tinggi filsafat teologi(gl-at)","gadang-sekolah tinggi filsafat teologi(ldg-at)","gadang-sekolah tinggi admisnistrasi(lg)","arjosari-sekolah tinggi admisnistrasi(al)","landungsari-sekolah tinggi admisnistrasi(gl)","arjosari-sekolah tinggi ilmu ekonomi(at)","landungsari-sekolah tinggi ilmu ekonomi(gl-at)","gadang-sekolah tinggi ilmu ekonomi(ldg-at)","arjosari-stie indocakti(adl)","gadang-stie indocakti(gl)","landungsari-stie indocakti(adl)",
                "arjosari-stie asia(abg)","gadang-stie asia(abg)","landungsari-stie asia(ckl-abg)","arjosari-stie kertanegara(ga-ckl-tst)","gadang-stie kertanegara(abg-tst)","landungsari-stie kertanegara(ckl-tst)",
                "arjosari-stie jaya negara(asd)","gadang-stie jaya negara(ajg)","landungsari-stie jaya negara(adl-asd)","arjosari-stie koperasi malang(al)","gadang-stie koperasi malang(ajg)","landungsari-stie koperasi malang(adl)",
                "arjosari-stip(ga-ckl-tst)","gadang-stip(abg-ckl-tst)","landungsari-stip(ckl-tst)","arjosari-stt internasional(asd)","gadang-stt internasional(amg)","landungsari-stt internasional(adl)",
                "arjosari-sekolah tinggi informatika(at)","landungsari-sekolah tinggi informatika(gl-at)","gadang-sekolah tinggi informatika(ldg-at)","landungsari-stikes maharani(ckl)","arjosari-stikes maharani(abg-ckl)","gadang-stikes maharani(abg-ckl)","arjosari-stikes widyagama(abg)","gadang-stikes widyagama(abg)","landungsari-stikes widyagama(ckl-abg)",
                "arjosari-stikes kendedes(adl)","gadang-stikes kendedes(amg)","landungsari-stikes kendedes(adl)","arjosari-stie malangkucecwara(abg)","gadang-stie malangkucecwara(abg)","landungsari-stie malangkucecwara(ckl-abg)","landungsari-stmik paramitha(adl)",
                "arjosari-stmik paramitha(asd)","gadang-stmik paramitha(amg)","arjosari-stt malang(abg)","gadang-stt malang(abg)","landungsari-stt malang(ckl-abg)",
                "arjosari-stai ma had aly al hikam(ga-ckl-tst)","gadang-stai ma had aly al hikam(abg-ckl-tst)","landungsari-stai ma had aly al hikam(ckl-tst)","arjosari-upbjj(g)","gadang-upbjj(gl)","arjosari-sekolah tinggi teologi(at)","gadang-sekolah tinggi teologi(ldg-at)","landungsari-sekolah tinggi teologi(gl-at)"};
        spiner = (Spinner) findViewById(R.id.spiner);
        ArrayAdapter<String> AdapterList = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,list);
        spiner.setAdapter(AdapterList);

        //spiner.getSelectedItem(Util.getProperty("name",getApplicationContext()));


        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String sp = parent.getItemAtPosition(position).toString();
                if(sp.equals("gadang-unikama(ga)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    jalur();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-unikama(ga)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_unikama();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-unikama(gl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_unikama();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-unisma(gl)")) {
                    landungsari_unisma();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-unisma(lg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_unisma();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-unisma(al)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_unisma();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-uin(al)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_uin();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-uin(lg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_uin();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-uin(gL)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_uin();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-umm(al)")) {
                    arjosari_umm();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-umm(lg)")) {
                    gadang_umm();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-ub(adl)")) {
                    arjosari_ub();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-ub(lg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_ub();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-ub(al)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_ub();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-um(al)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_um();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-um(gl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_um();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-um(al)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_um();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-itn(al)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_itn();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-itn(lg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_itn();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-itn(gl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_itn();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-polinema(adl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_polinema();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-polinema(adl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_polinema();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-polinema(lg)")) {
                    gadang_polinema();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stmik asia(abg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_stmik_asia();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stmik asia(abg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_stmik_asia();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stmik asia(adl)")) {
                    landungsari_stmik_asia();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-Unidha(ckl)")) {
                    landungsari_Unidha();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-Unidha(ajg-ckl)")) {
                    gadang_unidha();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-Unidha(al-ckl)")) {
                    arjosari_unidha();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-uwg(abg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_uwg();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-uwg(abg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_uwg();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-uwg(ckl-abg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_uwg();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-ukcw(adl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_ukcw();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-ukcw(adl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_ukcw();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-ukcw(gl-adl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_ukcw();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-univ katolik widya(lg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_univ_katolik_widya();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-univ katolik widya(al)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_univ_katolik_widya();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-univ katolik widya(lg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_univ_katolik_widya();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-ikip budi utomo(gl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_ikip_budi_utomo();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-ikip budi utomo(ldg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_ikip_budi_utomo();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-ikip budi utomo(at)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_ikip_budi_utomo();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-ipm(abg)")) {
                    arjosari_ipm();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-ipm(abg)")) {
                    gadang_ipm();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-ipm(ckl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_ipm();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-pum(gl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_pum();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-pum(lg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_pum();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-pum(al)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_pum();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-poltekes kemenkes(al)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_poltekes_kemenkes();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-poltekes kemenkes(adl)")) {
                    landungsari_poltekes_kemenkes();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-poltekes kemenkes(gl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_poltekes_kemenkes();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-poltekes soepraon(ga)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_poltekes_soepraon();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-poltekes soepraon(ga)")) {
                    arjosari_poltekes_soepraon();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-poltekes soepraon(gl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_poltekes_soepraon();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-akademi pariwisata dan hotel(adl)")) {
                    arjosari_akademi_pariwisata_hotel();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-akademi pariwisata dan hotel(adl)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_akademi_pariwisata_dan_hotel();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-akademi pariwisata dan hotel(amg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_akademi_pariwisata_hotel();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-akd farmasi putra(ajg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_akd_farmasi_putra();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-akd farmasi putra(asd)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_akd_farmasi_putra();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-akd farmasi putra(adl-asd)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    landungsari_akd_farmasi_putra();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-akd keperawatan waluyo(ag)")) {
                    gadang_akd_keperawatan_waluyo();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-akd keperawatan waluyo(gl)")) {
                landungsari_akd_keperawatan_waluyo();
                setUpMapIfNeeded();
            }else if(sp.equals("arjosari-akd keperawatan waluyo(ga-gl)")) {
                    arjosari_akd_keperawatan_waluyo();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-akd wira husada(lg)")) {
                    gadang_akd_wira_husada();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-akd wira husada(gl)")) {
                    landungsari_akd_wira_husada();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-akd wira husada(al)")) {
                    arjosari_akd_wira_husada();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-akd wijaya kusuma(abg)")) {
                    gadang_akbid_wijaya_kusuma();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-akd wijaya kusuma(adl)")) {
                    landungsari_akbid_wijaya_kusuma();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-akd wijaya kusuma(al)")) {
                    arjosari_akbid_wijaya_kusuma();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-akd analisis kesehatan(ckl)")) {
                    landungsari_akd_analisis_kesehatan();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-akd analisis kesehatan(ajg-ckl)")) {
                    gadang_akd_analisis_kesehatan();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-akd analisis kesehatan(al-ckl)")) {
                    arjosari_akd_analisis_kesehatan();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-balai diklat(ag)")) {
                    arjosari_balai_diklat();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-balai diklat(adl)")) {
                    landungsari_balai_diklat();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-balai diklat(abg)")) {
                    gadang_balai_diklat();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-sekolah atlas nusantara(adl)")) {
                    landungsari_atlas_nusantara();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-sekolah atlas nusantara(abg)")) {
                    gadang_atlas_nusantara();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-sekolah tinggi teknik(ckl)")) {
                    landungsari_stt();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-sekolah tinggi teknik(abg)")) {
                    arjosari_stt();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-sekolah tinggi teknik(abg)")) {
                    gadang_stt();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-sekolah tinggi bahasa asing(ckl)")) {
                    landungsari_stiba();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-sekolah tinggi bahasa asing(ajg-ckl)")) {
                    gadang_stiba();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-sekolah tinggi bahasa asing(al-ckl)")) {
                    arjosari_stiba();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-sekolah tinggi filsafat teologi(at)")) {
                    arjosari_stft();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-sekolah tinggi filsafat teologi(gl-at)")) {
                    landungsari_stft();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-sekolah tinggi filsafat teologi(ldg-at)")) {
                    gadang_stft();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-sekolah tinggi admisnistrasi(lg)")) {
                    gadang_stia();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-sekolah tinggi admisnistrasi(al)")) {
                    arjosari_stia();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-sekolah tinggi admisnistrasi(gl)")) {
                    landungsari_stia();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-sekolah tinggi ilmu ekonomi(at)")) {
                    arjosari_stie();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-sekolah tinggi ilmu ekonomi(gl-at)")) {
                    landungsari_stie();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-sekolah tinggi ilmu ekonomi(ldg-at)")) {
                    gadang_stie();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stie indocakti(adl)")) {
                    arjosari_stie_indocakti();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stie indocakti(gl)")) {
                    gadang_stie_indocakti();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stie indocakti(adl)")) {
                    landungsari_stie_indocakti();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stie asia(abg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    arjosari_stie_asia();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stie asia(abg)")) {
                    jalur_angkot angkot = new jalur_angkot();
                    gadang_stie_asia();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stie asia(ckl-abg)")) {
                    landungsari_stie_asia();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stie kertanegara(ga-ckl-tst)")) {
                    arjosari_stie_kertanegara();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stie kertanegara(abg-tst)")) {
                    gadang_stie_kertanegara();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stie kertanegara(ckl-tst)")) {
                    landungsari_stie_kertanegara();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stie jaya negara(asd)")) {
                    arjosari_stie_jaya_negara();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stie jaya negara(ajg)")) {
                    gadang_stie_jaya_negara();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stie jaya negara(adl-asd)")) {
                    landungsari_stie_jaya_negara();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stie koperasi malang(al)")) {
                    arjosari_stie_koperasi_malang();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stie koperasi malang(ajg)")) {
                    gadang_stie_koperasi_malang();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stie koperasi malang(adl)")) {
                    landungsari_stie_koperasi_malang();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stip(ga-ckl-tst)")) {
                    arjosari_stip();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stip(abg-ckl-tst)")) {
                    gadang_stip();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stip(ckl-tst)")) {
                    landungsari_stip();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stt internasional(asd)")) {
                    arjosari_stt_internasional();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stt internasional(amg)")) {
                    gadang_stt_internasional();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stt internasional(adl)")) {
                    landungsari_stt_internasional();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-sekolah tinggi informatika(at)")) {
                    arjosari_stiki();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-sekolah tinggi informatika(gl-at)")) {
                    landungsari_stiki();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-sekolah tinggi informatika(ldg-at)")) {
                    gadang_stiki();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stikes maharani(ckl)")) {
                    landungsari_stikes_maharani();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stikes maharani(abg-ckl)")) {
                    arjosari_stikes_maharani();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stikes maharani(abg-ckl)")) {
                    gadang_stikes_maharani();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stikes widyagama(abg)")) {
                    arjosari_stikes_widyagama();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stikes widyagama(abg)")) {
                    gadang_stikes_widyagama();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stikes widyagama(ckl-abg)")) {
                    landungsari_stikes_widyagama();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stikes kendedes(adl)")) {
                    arjosari_stikes_kendedes();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stikes kendedes(amg)")) {
                    gadang_stikes_kendedes();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stikes kendedes(adl)")) {
                    landungsari_stikes_kendedes();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stie malangkucecwara(abg)")) {
                    arjosari_stie_malangkucecwara();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stie malangkucecwara(abg)")) {
                    gadang_stie_malangkucecwara();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stie malangkucecwara(ckl-abg)")) {
                    landungsari_stie_malangkucecwara();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stmik paramitha(asd)")) {
                    arjosari_stmik_paramitha();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stmik paramitha(amg)")) {
                    gadang_stmik_paramitha();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stmik paramitha(adl)")) {
                    landungsari_stmik_paramitha();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stt malang(abg)")) {
                    arjosari_stt_malang();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stt malang(abg)")) {
                    gadang_stt_malang();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stt malang(ckl-abg)")) {
                    landungsari_stt_malang();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-stai ma had aly al hikam(ga-ckl-tst)")) {
                    arjosari_stai_ma_had_aly_al_hikam();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-stai ma had aly al hikam(abg-ckl-tst)")) {
                    gadang_stai_ma_had_aly_al_hikam();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-stai ma had aly al hikam(ckl-tst)")) {
                    landungsari_stai_ma_had_aly_al_hikam();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-upbjj(g)")) {
                    arjosari_upbjj();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-upbjj(gl)")) {
                    landungsari_upbjj();
                    setUpMapIfNeeded();
                }else if(sp.equals("arjosari-sekolah tinggi teologi(at)")) {
                    arjosari_sekolah_tinggi_teologi();
                    setUpMapIfNeeded();
                }else if(sp.equals("gadang-sekolah tinggi teologi(ldg-at)")) {
                    arjosari_sekolah_tinggi_teologi();
                    setUpMapIfNeeded();
                }else if(sp.equals("landungsari-sekolah tinggi teologi(gl-at)")) {
                    landungsari_sekolah_tinggi_teologi();
                    setUpMapIfNeeded();
                }
            }


            public void jalur(){//ga
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883));
            }

            public void arjosari_unikama(){//ga
                mRouteExample=GoogleMapHelper.createMapRoute(new LatLng(-7.93299,112.65769),
                        new LatLng(-7.93285,112.65687),new LatLng(-7.93212,112.65512),new LatLng(-7.93027,112.65068),
                        new LatLng(-7.92937,112.6489),new LatLng(-7.93077,112.6481),new LatLng(-7.93581,112.64524),
                        new LatLng(-7.94315,112.64165),new LatLng(-7.94546,112.64075),new LatLng(-7.94753,112.64012),
                        new LatLng(-7.95505,112.6385),new LatLng(-7.95707,112.63804),new LatLng(-7.96041,112.637),
                        new LatLng(-7.96074,112.63684),new LatLng(-7.96227,112.63615),new LatLng(-7.96452,112.63496),
                        new LatLng(-7.96478,112.63549),new LatLng(-7.96552,112.6368),new LatLng(-7.96598,112.63761),
                        new LatLng(-7.96633,112.63821),new LatLng(-7.9667,112.63823),new LatLng(-7.96694,112.63851),
                        new LatLng(-7.96708,112.63873),new LatLng(-7.96736,112.63862),new LatLng(-7.96759,112.6383),
                        new LatLng(-7.96855,112.63833),new LatLng(-7.97038,112.63844),new LatLng(-7.97347,112.63856),
                        new LatLng(-7.97346,112.63788),new LatLng(-7.97338,112.63667),new LatLng(-7.97747,112.63683),
                        new LatLng(-7.97767,112.63683),new LatLng(-7.9773,112.63464),new LatLng(-7.97755,112.63452),
                        new LatLng(-7.97768,112.63431),new LatLng(-7.97773,112.63397),new LatLng(-7.97758,112.63361),
                        new LatLng(-7.97874,112.63257),new LatLng(-7.9788,112.6325),new LatLng(-7.97912,112.63142),
                        new LatLng(-7.97936,112.63118),new LatLng(-7.98025,112.63089),new LatLng(-7.98046,112.63066),
                        new LatLng(-7.98066,112.6306),new LatLng(-7.98159,112.63041),new LatLng(-7.98187,112.63168),
                        new LatLng(-7.9836,112.63133),new LatLng(-7.98522,112.63093),new LatLng(-7.98739,112.63043),
                        new LatLng(-7.98735,112.63031),new LatLng(-7.98822,112.62995),new LatLng(-7.98859,112.62968),
                        new LatLng(-7.9886,112.62962),new LatLng(-7.98831,112.62817),new LatLng(-7.98785,112.62588),
                        new LatLng(-7.98751,112.62508),new LatLng(-7.98654,112.62354),new LatLng(-7.98812,112.62223),
                        new LatLng(-7.98876,112.62204),new LatLng(-7.99094,112.62141),new LatLng(-8.00117,112.61788),
                        new LatLng(-8.00284,112.61773),new LatLng(-8.00428,112.61799),new LatLng(-8.00703,112.61884));
            }

            public void landungsari_unikama() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.9432, 112.61025),
                        new LatLng(-7.9434, 112.61015), new LatLng(-7.94681, 112.60893), new LatLng(-7.94698, 112.60887),
                        new LatLng(-7.9471, 112.60883), new LatLng(-7.94738, 112.60881), new LatLng(-7.94797, 112.60875),
                        new LatLng(-7.94815, 112.60876), new LatLng(-7.95068, 112.60889), new LatLng(-7.95092, 112.60891),
                        new LatLng(-7.95141, 112.60899), new LatLng(-7.95147, 112.60901), new LatLng(-7.95208, 112.60919),
                        new LatLng(-7.95227, 112.60928), new LatLng(-7.95291, 112.6098), new LatLng(-7.95358, 112.61029),
                        new LatLng(-7.9543, 112.6106), new LatLng(-7.95665, 112.61275), new LatLng(-7.95679, 112.61286),
                        new LatLng(-7.95755, 112.61347), new LatLng(-7.95777, 112.61353), new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.96094, 112.61364), new LatLng(-7.96175, 112.61339), new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.965, 112.61357), new LatLng(-7.97044, 112.6134), new LatLng(-7.97175, 112.61322),
                        new LatLng(-7.97196, 112.61329), new LatLng(-7.97315, 112.61313), new LatLng(-7.97374, 112.61304),
                        new LatLng(-7.97684, 112.61252), new LatLng(-7.97749, 112.61256), new LatLng(-7.97768, 112.61263),
                        new LatLng(-7.97829, 112.61291), new LatLng(-7.98006, 112.61412), new LatLng(-7.9814, 112.6147),
                        new LatLng(-7.98163, 112.61472), new LatLng(-7.98211, 112.61466), new LatLng(-7.98355, 112.61431),
                        new LatLng(-7.9834, 112.61501), new LatLng(-7.98334, 112.6156), new LatLng(-7.98354, 112.61884),
                        new LatLng(-7.98361, 112.6199), new LatLng(-7.98354, 112.62057), new LatLng(-7.98327, 112.62164),
                        new LatLng(-7.98323, 112.62204), new LatLng(-7.98359, 112.62444), new LatLng(-7.98407, 112.62611),
                        new LatLng(-7.98572, 112.62417), new LatLng(-7.98804, 112.62229), new LatLng(-7.98843, 112.62211),
                        new LatLng(-7.98892, 112.62203), new LatLng(-7.98998, 112.62171), new LatLng(-7.99094, 112.62141), new LatLng(-8.00117, 112.61788),
                        new LatLng(-8.00284, 112.61773), new LatLng(-8.00428, 112.61799), new LatLng(-8.00703, 112.61884));

            }
            public void landungsari_unisma() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.93725, 112.60621));

            }

            public void gadang_unisma() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338),
                        new LatLng(-7.97044, 112.6134),new LatLng(-7.965, 112.61357),new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96175, 112.61339),new LatLng(-7.96094, 112.61364),new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.95777, 112.61353),new LatLng(-7.95755, 112.61347),new LatLng(-7.95679, 112.61286),new LatLng(-7.95665, 112.61275),new LatLng(-7.9543, 112.6106),
                        new LatLng(-7.95358, 112.61029),new LatLng(-7.95291, 112.6098),new LatLng(-7.95227, 112.60928),
                        new LatLng(-7.95208, 112.60919),new LatLng(-7.95147, 112.60901),new LatLng(-7.95141, 112.60899),
                        new LatLng(-7.95092, 112.60891),new LatLng(-7.95068, 112.60889),new LatLng(-7.94815, 112.60876),
                        new LatLng(-7.94797, 112.60875),new LatLng(-7.94738, 112.60881),new LatLng(-7.9471, 112.60883),
                        new LatLng(-7.94698, 112.60887),new LatLng(-7.94681, 112.60893),new LatLng(-7.9434, 112.61015),
                        new LatLng(-7.9432, 112.61025),new LatLng(-7.94313, 112.61032),new LatLng(-7.93963, 112.60786),new LatLng(-7.9377, 112.60652));
            }

            public void arjosari_unisma() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95461, 112.62051), new LatLng(-7.95291, 112.61903),
                        new LatLng(-7.94996, 112.61545), new LatLng(-7.94985, 112.61536), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94616, 112.61238), new LatLng(-7.94451, 112.61131),
                        new LatLng(-7.94214, 112.60957), new LatLng(-7.93964, 112.60786), new LatLng(-7.93729, 112.60623));
            }

            public void arjosari_uin() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95795, 112.6234), new LatLng(-7.95803, 112.62335), new LatLng(-7.95853, 112.62283),
                        new LatLng(-7.95911, 112.62236), new LatLng(-7.95996, 112.6217), new LatLng(-7.96006, 112.6216),
                        new LatLng(-7.95832, 112.61945), new LatLng(-7.95657, 112.61642), new LatLng(-7.95652, 112.61636),
                        new LatLng(-7.9562, 112.61578), new LatLng(-7.95606, 112.61555), new LatLng(-7.95601, 112.61537),
                        new LatLng(-7.9564, 112.6135), new LatLng(-7.95651, 112.61321), new LatLng(-7.95675, 112.61282),
                        new LatLng(-7.95679, 112.61271), new LatLng(-7.95666, 112.61275), new LatLng(-7.95466, 112.61087),
                        new LatLng(-7.95429, 112.61059), new LatLng(-7.95414, 112.6105),
                        new LatLng(-7.95358, 112.61028), new LatLng(-7.95227, 112.60928), new LatLng(-7.95208, 112.60919));

            }

            public void gadang_uin() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338),
                        new LatLng(-7.97044, 112.6134), new LatLng(-7.965, 112.61357), new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96175, 112.61339), new LatLng(-7.96094, 112.61364), new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.95777, 112.61353), new LatLng(-7.95755, 112.61347), new LatLng(-7.95679, 112.61286), new LatLng(-7.95665, 112.61275), new LatLng(-7.9543, 112.6106),
                        new LatLng(-7.95358, 112.61029), new LatLng(-7.95291, 112.6098), new LatLng(-7.95227, 112.60928),
                        new LatLng(-7.95208, 112.60919));
            }

            public void landungsari_uin() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.9432, 112.61025),
                        new LatLng(-7.9434, 112.61015), new LatLng(-7.94681, 112.60893), new LatLng(-7.94698, 112.60887),
                        new LatLng(-7.9471, 112.60883), new LatLng(-7.94738, 112.60881), new LatLng(-7.94797, 112.60875),
                        new LatLng(-7.94815, 112.60876), new LatLng(-7.95068, 112.60889), new LatLng(-7.95092, 112.60891),
                        new LatLng(-7.95141, 112.60899), new LatLng(-7.95147, 112.60901), new LatLng(-7.95208, 112.60919));
            }

            public void arjosari_umm() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),

                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984),
                        new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95461, 112.62051), new LatLng(-7.95291, 112.61903),
                        new LatLng(-7.94996, 112.61545), new LatLng(-7.94985, 112.61536), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94616, 112.61238), new LatLng(-7.94451, 112.61131),
                        new LatLng(-7.94214, 112.60957), new LatLng(-7.93964, 112.60786), new LatLng(-7.93729, 112.60623),
                        new LatLng(-7.93598, 112.60533),new LatLng(-7.93316, 112.60338),new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93176, 112.6026),new LatLng(-7.93145, 112.60262),new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.92863, 112.60294),new LatLng(-7.92764, 112.60279),new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.9252, 112.60042),new LatLng(-7.92414, 112.599),new LatLng(-7.92489, 112.59844));
            }

            public void gadang_umm() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338),
                        new LatLng(-7.97044, 112.6134),new LatLng(-7.965, 112.61357),new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96175, 112.61339),new LatLng(-7.96094, 112.61364),new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.95777, 112.61353),new LatLng(-7.95755, 112.61347),new LatLng(-7.95679, 112.61286),new LatLng(-7.95665, 112.61275),new LatLng(-7.9543, 112.6106),
                        new LatLng(-7.95358, 112.61029),new LatLng(-7.95291, 112.6098),new LatLng(-7.95227, 112.60928),
                        new LatLng(-7.95208, 112.60919),new LatLng(-7.95147, 112.60901),new LatLng(-7.95141, 112.60899),
                        new LatLng(-7.95092, 112.60891),new LatLng(-7.95068, 112.60889),new LatLng(-7.94815, 112.60876),
                        new LatLng(-7.94797, 112.60875),new LatLng(-7.94738, 112.60881),new LatLng(-7.9471, 112.60883),
                        new LatLng(-7.94698, 112.60887),new LatLng(-7.94681, 112.60893),new LatLng(-7.9434, 112.61015),
                        new LatLng(-7.9432, 112.61025),new LatLng(-7.94313, 112.61032),new LatLng(-7.93963, 112.60786),new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93598, 112.60533),new LatLng(-7.93316, 112.60338),new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93176, 112.6026),new LatLng(-7.93145, 112.60262),new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.92863, 112.60294),new LatLng(-7.92764, 112.60279),new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.9252, 112.60042),new LatLng(-7.92414, 112.599),new LatLng(-7.92489, 112.59844));
            }

            public void arjosari_ub() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),new LatLng(-7.96789, 112.63313),
                        new LatLng(-7.97344, 112.63045), new LatLng(-7.97443, 112.62987), new LatLng(-7.97555, 112.62951),new LatLng(-7.97648, 112.62946),
                        new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95461, 112.62051), new LatLng(-7.95291, 112.61903),
                        new LatLng(-7.94996, 112.61545), new LatLng(-7.94985, 112.61536),new LatLng(-7.94908, 112.61462));
            }

            public void gadang_ub() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338),
                        new LatLng(-7.97044, 112.6134), new LatLng(-7.965, 112.61357), new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96175, 112.61339), new LatLng(-7.96094, 112.61364), new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.95777, 112.61353), new LatLng(-7.95755, 112.61347), new LatLng(-7.95679, 112.61286), new LatLng(-7.95665, 112.61275), new LatLng(-7.9543, 112.6106),
                        new LatLng(-7.95358, 112.61029));
            }

            public void landungsari_ub() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.9432, 112.61025),
                        new LatLng(-7.9434, 112.61015), new LatLng(-7.94681, 112.60893), new LatLng(-7.94698, 112.60887),
                        new LatLng(-7.9471, 112.60883), new LatLng(-7.94738, 112.60881), new LatLng(-7.94797, 112.60875),
                        new LatLng(-7.94815, 112.60876), new LatLng(-7.95068, 112.60889), new LatLng(-7.95092, 112.60891),
                        new LatLng(-7.95141, 112.60899), new LatLng(-7.95147, 112.60901), new LatLng(-7.95208, 112.60919),
                        new LatLng(-7.95227, 112.60928), new LatLng(-7.95291, 112.6098),new LatLng(-7.95358, 112.61029),
                        new LatLng(-7.9543, 112.6106));
            }

            public void arjosari_um() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95795, 112.6234), new LatLng(-7.95803, 112.62335), new LatLng(-7.95853, 112.62283),
                        new LatLng(-7.95911, 112.62236), new LatLng(-7.95996, 112.6217), new LatLng(-7.96006, 112.6216),
                        new LatLng(-7.95832, 112.61945));
            }

            public void gadang_um() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338), new LatLng(-7.97044, 112.6134),new LatLng(-7.965, 112.61357),

                        new LatLng(-7.96393, 112.61352), new LatLng(-7.96391, 112.61405), new LatLng(-7.96398, 112.61439),
                        new LatLng(-7.96427, 112.6151), new LatLng(-7.96443, 112.61531), new LatLng(-7.96449, 112.61542),
                        new LatLng(-7.9646, 112.61572), new LatLng(-7.96467, 112.61587), new LatLng(-7.96635, 112.61868),
                        new LatLng(-7.96671, 112.61938), new LatLng(-7.96442, 112.62066), new LatLng(-7.96429, 112.62077),
                        new LatLng(-7.96411, 112.62095), new LatLng(-7.96382, 112.62131), new LatLng(-7.96343, 112.62195),
                        new LatLng(-7.96328, 112.62228), new LatLng(-7.96305, 112.62331), new LatLng(-7.96302, 112.62361),
                        new LatLng(-7.96304, 112.6238), new LatLng(-7.96308, 112.62413), new LatLng(-7.96335, 112.62547),
                        new LatLng(-7.96266, 112.62573), new LatLng(-7.9623, 112.62584),
                        new LatLng(-7.96225, 112.62581), new LatLng(-7.96217, 112.62518), new LatLng(-7.96212, 112.62492),
                        new LatLng(-7.96209, 112.62474), new LatLng(-7.96211, 112.62396), new LatLng(-7.96189, 112.62353),
                        new LatLng(-7.96173, 112.62347), new LatLng(-7.9616, 112.62347), new LatLng(-7.96149, 112.62341),
                        new LatLng(-7.9614, 112.62333), new LatLng(-7.96007, 112.6216), new LatLng(-7.95861, 112.61985));
            }

            public void landungsari_um() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.9432, 112.61025),
                        new LatLng(-7.9434, 112.61015), new LatLng(-7.94681, 112.60893), new LatLng(-7.94698, 112.60887),
                        new LatLng(-7.9471, 112.60883), new LatLng(-7.94738, 112.60881), new LatLng(-7.94797, 112.60875),
                        new LatLng(-7.94815, 112.60876), new LatLng(-7.95068, 112.60889), new LatLng(-7.95092, 112.60891),
                        new LatLng(-7.95141, 112.60899), new LatLng(-7.95147, 112.60901), new LatLng(-7.95208, 112.60919),
                        new LatLng(-7.95227, 112.60928), new LatLng(-7.95291, 112.6098), new LatLng(-7.95358, 112.61029),
                        new LatLng(-7.9543, 112.6106), new LatLng(-7.95665, 112.61275),
                        new LatLng(-7.95628, 112.61331), new LatLng(-7.95623, 112.61344), new LatLng(-7.95587, 112.61537),
                        new LatLng(-7.95588, 112.61551), new LatLng(-7.95608, 112.61596), new LatLng(-7.95621, 112.61629),
                        new LatLng(-7.95809, 112.61944));
            }

            public void arjosari_itn() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95795, 112.6234), new LatLng(-7.95803, 112.62335), new LatLng(-7.95853, 112.62283),
                        new LatLng(-7.95911, 112.62236), new LatLng(-7.95996, 112.6217), new LatLng(-7.96006, 112.6216),
                        new LatLng(-7.95832, 112.61945),
                        new LatLng(-7.95657, 112.61642), new LatLng(-7.95652, 112.61636),
                        new LatLng(-7.9562, 112.61578), new LatLng(-7.95606, 112.61555), new LatLng(-7.95601, 112.61537),
                        new LatLng(-7.9564, 112.6135), new LatLng(-7.95651, 112.61321), new LatLng(-7.95675, 112.61282));
            }

            public void gadang_itn() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338),
                        new LatLng(-7.97044, 112.6134), new LatLng(-7.965, 112.61357), new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96175, 112.61339), new LatLng(-7.96094, 112.61364), new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.95777, 112.61353), new LatLng(-7.95755, 112.61347), new LatLng(-7.95679, 112.61286));
            }

            public void landungsari_itn() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.9432, 112.61025),
                        new LatLng(-7.9434, 112.61015), new LatLng(-7.94681, 112.60893), new LatLng(-7.94698, 112.60887),
                        new LatLng(-7.9471, 112.60883), new LatLng(-7.94738, 112.60881), new LatLng(-7.94797, 112.60875),
                        new LatLng(-7.94815, 112.60876), new LatLng(-7.95068, 112.60889), new LatLng(-7.95092, 112.60891),
                        new LatLng(-7.95141, 112.60899), new LatLng(-7.95147, 112.60901), new LatLng(-7.95208, 112.60919),
                        new LatLng(-7.95227, 112.60928), new LatLng(-7.95291, 112.6098), new LatLng(-7.95358, 112.61029),
                        new LatLng(-7.9543, 112.6106), new LatLng(-7.95665, 112.61275), new LatLng(-7.95679, 112.61286));
            }

            public void arjosari_polinema() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496), new LatLng(-7.96789, 112.63313),
                        new LatLng(-7.97344, 112.63045), new LatLng(-7.97443, 112.62987), new LatLng(-7.97555, 112.62951), new LatLng(-7.97648, 112.62946),
                        new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95461, 112.62051), new LatLng(-7.95291, 112.61903),
                        new LatLng(-7.94996, 112.61545), new LatLng(-7.94985, 112.61536));
            }

            public void landungsari_polinema() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536));
            }

            public void gadang_polinema() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338), new LatLng(-7.97044, 112.6134), new LatLng(-7.965, 112.61357),
                        new LatLng(-7.96393, 112.61352), new LatLng(-7.96391, 112.61405), new LatLng(-7.96398, 112.61439),
                        new LatLng(-7.96427, 112.6151), new LatLng(-7.96443, 112.61531), new LatLng(-7.96449, 112.61542),
                        new LatLng(-7.9646, 112.61572), new LatLng(-7.96467, 112.61587), new LatLng(-7.96635, 112.61868),
                        new LatLng(-7.96671, 112.61938), new LatLng(-7.96442, 112.62066), new LatLng(-7.96429, 112.62077),
                        new LatLng(-7.96411, 112.62095), new LatLng(-7.96382, 112.62131),
                        new LatLng(-7.96351, 112.6218), new LatLng(-7.96278, 112.62143), new LatLng(-7.96242, 112.62127),
                        new LatLng(-7.96137, 112.62109), new LatLng(-7.96101, 112.62114), new LatLng(-7.96074, 112.62121),
                        new LatLng(-7.96035, 112.6214), new LatLng(-7.96007, 112.6216), new LatLng(-7.95997, 112.62171),
                        new LatLng(-7.95855, 112.62282),new LatLng(-7.95802, 112.62336), new LatLng(-7.95795, 112.62339), new LatLng(-7.95785, 112.62342),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95461, 112.62051), new LatLng(-7.95291, 112.61903),
                        new LatLng(-7.94996, 112.61545), new LatLng(-7.94985, 112.61536));
            }

            public void arjosari_stmik_asia() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211), new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704));
            }

            public void gadang_stmik_asia() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213));
            }

            public void landungsari_stmik_asia() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603),new LatLng(-7.97526, 112.62614),new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624),new LatLng(-7.97548, 112.62623),new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653),new LatLng(-7.97596, 112.62686),new LatLng(-7.97593, 112.62694),new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701),new LatLng(-7.97623, 112.62747),new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773),new LatLng(-7.97642, 112.62788),new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987),new LatLng(-7.97647, 112.63007),new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156),new LatLng(-7.97698, 112.63346),new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348),new LatLng(-7.97758, 112.63361),new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431),new LatLng(-7.97755, 112.63452),new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683),new LatLng(-7.97747, 112.63683),new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788),new LatLng(-7.97347, 112.63856),new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833),new LatLng(-7.96759, 112.6383),new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873),new LatLng(-7.96694, 112.63851),new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821),new LatLng(-7.96598, 112.63761),new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549),new LatLng(-7.96452, 112.63496),new LatLng(-7.96227, 112.63615),
                        new LatLng(-7.96074, 112.63684),new LatLng(-7.96041, 112.637),new LatLng(-7.95707, 112.63804),
                        new LatLng(-7.95505, 112.6385),new LatLng(-7.94753, 112.64012),new LatLng(-7.94546, 112.64075),
                        new LatLng(-7.94315, 112.64165),new LatLng(-7.94185, 112.64223));
            }

            public void landungsari_Unidha() {//ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786),new LatLng(-7.94214, 112.60957),
                        new LatLng(-7.94279, 112.61008), new LatLng(-7.94156, 112.61125), new LatLng(-7.94114, 112.61085),
                        new LatLng(-7.94043, 112.61145), new LatLng(-7.9402, 112.61159), new LatLng(-7.93978, 112.61178),
                        new LatLng(-7.93926, 112.61185), new LatLng(-7.93861, 112.61188),
                        new LatLng(-7.93812, 112.61182), new LatLng(-7.93787, 112.61176), new LatLng(-7.93745, 112.61152),
                        new LatLng(-7.9373, 112.61152), new LatLng(-7.93706, 112.61163), new LatLng(-7.93664, 112.61186),
                        new LatLng(-7.93625, 112.61206), new LatLng(-7.93555, 112.61242), new LatLng(-7.93532, 112.6126),
                        new LatLng(-7.93494, 112.61313), new LatLng(-7.93482, 112.61315), new LatLng(-7.9345, 112.61316),
                        new LatLng(-7.93387, 112.61321), new LatLng(-7.93303, 112.61329), new LatLng(-7.93312, 112.61338),
                        new LatLng(-7.93447, 112.61524), new LatLng(-7.93479, 112.61579),new LatLng(-7.93547, 112.61723),new LatLng(-7.93611, 112.61829),
                        new LatLng(-7.93631, 112.61865), new LatLng(-7.93631, 112.61865), new LatLng(-7.9367, 112.61927),
                        new LatLng(-7.93691, 112.61976), new LatLng(-7.9371, 112.62005),new LatLng(-7.93764, 112.62085), new LatLng(-7.93782, 112.62118),
                        new LatLng(-7.93827, 112.62225), new LatLng(-7.93844, 112.62292), new LatLng(-7.93905, 112.62409),
                        new LatLng(-7.93944, 112.62487), new LatLng(-7.93953, 112.625), new LatLng(-7.93965, 112.62488),
                        new LatLng(-7.94037, 112.62404), new LatLng(-7.94153, 112.62272), new LatLng(-7.94461, 112.61948),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95122, 112.63285),
                        new LatLng(-7.95158, 112.63387), new LatLng(-7.95196, 112.63461),
                        new LatLng(-7.95206, 112.63477), new LatLng(-7.9523, 112.63529), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.95257, 112.63612), new LatLng(-7.95252, 112.63634),
                        new LatLng(-7.95253, 112.63654), new LatLng(-7.95263, 112.63707), new LatLng(-7.95275, 112.63781),
                        new LatLng(-7.95297, 112.63884), new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.9736, 112.63856), new LatLng(-7.97533, 112.64104), new LatLng(-7.97546, 112.64119),
                        new LatLng(-7.97552, 112.64127), new LatLng(-7.97744, 112.64352), new LatLng(-7.97854, 112.6458),
                        new LatLng(-7.97864, 112.64621), new LatLng(-7.97884, 112.64758), new LatLng(-7.97905, 112.6493),
                        new LatLng(-7.97918, 112.64975), new LatLng(-7.97975, 112.65143), new LatLng(-7.98031, 112.65263),
                        new LatLng(-7.98088, 112.65355), new LatLng(-7.98094, 112.65368),
                        new LatLng(-7.97945, 112.65428), new LatLng(-7.97652, 112.65491), new LatLng(-7.97639, 112.65495),
                        new LatLng(-7.97536, 112.65516), new LatLng(-7.97532, 112.65518),
                        new LatLng(-7.97349, 112.65591), new LatLng(-7.97142, 112.65675), new LatLng(-7.96837, 112.65816),
                        new LatLng(-7.96745, 112.65879), new LatLng(-7.96642, 112.65965), new LatLng(-7.9663, 112.65972),
                        new LatLng(-7.96655, 112.66027), new LatLng(-7.96679, 112.66084), new LatLng(-7.96731, 112.66204),
                        new LatLng(-7.96789, 112.66172), new LatLng(-7.96928, 112.66095), new LatLng(-7.96985, 112.66062),
                        new LatLng(-7.96999, 112.66054), new LatLng(-7.97013, 112.66053),
                        new LatLng(-7.97042, 112.66055), new LatLng(-7.97059, 112.66062), new LatLng(-7.97161, 112.66132),
                        new LatLng(-7.97178, 112.66148), new LatLng(-7.97276, 112.66328),
                        new LatLng(-7.9724, 112.66352), new LatLng(-7.97289, 112.66439), new LatLng(-7.97371, 112.66589),
                        new LatLng(-7.97394, 112.66629), new LatLng(-7.9727, 112.66692),
                        new LatLng(-7.97254, 112.66702), new LatLng(-7.97227, 112.66714), new LatLng(-7.97216, 112.66723),
                        new LatLng(-7.97217, 112.66731), new LatLng(-7.97221, 112.66734), new LatLng(-7.97241, 112.66753), new LatLng(-7.97272, 112.6678),
                        new LatLng(-7.97277, 112.66789), new LatLng(-7.97273, 112.66797), new LatLng(-7.97263, 112.66802),
                        new LatLng(-7.97224, 112.66819), new LatLng(-7.97174, 112.66848), new LatLng(-7.97111, 112.66879));
            }

            public void arjosari_unidha() {//al-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.9736, 112.63856), new LatLng(-7.97533, 112.64104), new LatLng(-7.97546, 112.64119),
                        new LatLng(-7.97552, 112.64127), new LatLng(-7.97744, 112.64352), new LatLng(-7.97854, 112.6458),
                        new LatLng(-7.97864, 112.64621), new LatLng(-7.97884, 112.64758), new LatLng(-7.97905, 112.6493),
                        new LatLng(-7.97918, 112.64975), new LatLng(-7.97975, 112.65143), new LatLng(-7.98031, 112.65263),
                        new LatLng(-7.98088, 112.65355), new LatLng(-7.98094, 112.65368),
                        new LatLng(-7.97945, 112.65428), new LatLng(-7.97652, 112.65491), new LatLng(-7.97639, 112.65495),
                        new LatLng(-7.97536, 112.65516), new LatLng(-7.97532, 112.65518),
                        new LatLng(-7.97349, 112.65591), new LatLng(-7.97142, 112.65675), new LatLng(-7.96837, 112.65816),
                        new LatLng(-7.96745, 112.65879), new LatLng(-7.96642, 112.65965), new LatLng(-7.9663, 112.65972),
                        new LatLng(-7.96655, 112.66027), new LatLng(-7.96679, 112.66084), new LatLng(-7.96731, 112.66204),
                        new LatLng(-7.96789, 112.66172), new LatLng(-7.96928, 112.66095), new LatLng(-7.96985, 112.66062),
                        new LatLng(-7.96999, 112.66054), new LatLng(-7.97013, 112.66053),
                        new LatLng(-7.97042, 112.66055), new LatLng(-7.97059, 112.66062), new LatLng(-7.97161, 112.66132),
                        new LatLng(-7.97178, 112.66148), new LatLng(-7.97276, 112.66328),
                        new LatLng(-7.9724, 112.66352), new LatLng(-7.97289, 112.66439), new LatLng(-7.97371, 112.66589),
                        new LatLng(-7.97394, 112.66629), new LatLng(-7.9727, 112.66692),
                        new LatLng(-7.97254, 112.66702), new LatLng(-7.97227, 112.66714), new LatLng(-7.97216, 112.66723),
                        new LatLng(-7.97217, 112.66731), new LatLng(-7.97221, 112.66734), new LatLng(-7.97241, 112.66753), new LatLng(-7.97272, 112.6678),
                        new LatLng(-7.97277, 112.66789), new LatLng(-7.97273, 112.66797), new LatLng(-7.97263, 112.66802),
                        new LatLng(-7.97224, 112.66819), new LatLng(-7.97174, 112.66848), new LatLng(-7.97111, 112.66879));
            }

            public void gadang_unidha() {//ajg-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99672, 112.6297), new LatLng(-7.99288, 112.63141), new LatLng(-7.993, 112.63174),
                        new LatLng(-7.99307, 112.63193), new LatLng(-7.99352, 112.6335),
                        new LatLng(-7.99234, 112.63379), new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389),
                        new LatLng(-7.99137, 112.63408), new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442),
                        new LatLng(-7.98928, 112.63488), new LatLng(-7.98847, 112.633), new LatLng(-7.98672, 112.63372),
                        new LatLng(-7.98622, 112.63394), new LatLng(-7.986, 112.6335), new LatLng(-7.98353, 112.63424),
                        new LatLng(-7.98168, 112.63484), new LatLng(-7.98189, 112.63543), new LatLng(-7.98279, 112.63691),
                        new LatLng(-7.98295, 112.63712), new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97343, 112.63668),
                        new LatLng(-7.97341, 112.63681), new LatLng(-7.97342, 112.63714), new LatLng(-7.97342, 112.63724),new LatLng(-7.97347, 112.63819), new LatLng(-7.97348, 112.63855),
                        new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.9736, 112.63856), new LatLng(-7.97533, 112.64104), new LatLng(-7.97546, 112.64119),
                        new LatLng(-7.97552, 112.64127), new LatLng(-7.97744, 112.64352), new LatLng(-7.97854, 112.6458),
                        new LatLng(-7.97864, 112.64621), new LatLng(-7.97884, 112.64758), new LatLng(-7.97905, 112.6493),
                        new LatLng(-7.97918, 112.64975), new LatLng(-7.97975, 112.65143), new LatLng(-7.98031, 112.65263),
                        new LatLng(-7.98088, 112.65355), new LatLng(-7.98094, 112.65368),
                        new LatLng(-7.97945, 112.65428), new LatLng(-7.97652, 112.65491), new LatLng(-7.97639, 112.65495),
                        new LatLng(-7.97536, 112.65516), new LatLng(-7.97532, 112.65518),
                        new LatLng(-7.97349, 112.65591), new LatLng(-7.97142, 112.65675), new LatLng(-7.96837, 112.65816),
                        new LatLng(-7.96745, 112.65879), new LatLng(-7.96642, 112.65965), new LatLng(-7.9663, 112.65972),
                        new LatLng(-7.96655, 112.66027), new LatLng(-7.96679, 112.66084), new LatLng(-7.96731, 112.66204),
                        new LatLng(-7.96789, 112.66172), new LatLng(-7.96928, 112.66095), new LatLng(-7.96985, 112.66062),
                        new LatLng(-7.96999, 112.66054), new LatLng(-7.97013, 112.66053),
                        new LatLng(-7.97042, 112.66055), new LatLng(-7.97059, 112.66062), new LatLng(-7.97161, 112.66132),
                        new LatLng(-7.97178, 112.66148), new LatLng(-7.97276, 112.66328),
                        new LatLng(-7.9724, 112.66352), new LatLng(-7.97289, 112.66439), new LatLng(-7.97371, 112.66589),
                        new LatLng(-7.97394, 112.66629), new LatLng(-7.9727, 112.66692),
                        new LatLng(-7.97254, 112.66702), new LatLng(-7.97227, 112.66714), new LatLng(-7.97216, 112.66723),
                        new LatLng(-7.97217, 112.66731), new LatLng(-7.97221, 112.66734), new LatLng(-7.97241, 112.66753), new LatLng(-7.97272, 112.6678),
                        new LatLng(-7.97277, 112.66789), new LatLng(-7.97273, 112.66797), new LatLng(-7.97263, 112.66802),
                        new LatLng(-7.97224, 112.66819), new LatLng(-7.97174, 112.66848), new LatLng(-7.97111, 112.66879));
            }

            public void arjosari_uwg() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211), new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606));
            }

            public void gadang_uwg() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606));
            }

            public void landungsari_uwg() {//ckl-abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),
                        new LatLng(-7.94934, 112.61572), new LatLng(-7.94575, 112.61828), new LatLng(-7.94451, 112.61934),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95122, 112.63285),
                        new LatLng(-7.95158, 112.63387), new LatLng(-7.95196, 112.63461),
                        new LatLng(-7.95206, 112.63477), new LatLng(-7.9523, 112.63529), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.95257, 112.63612), new LatLng(-7.95252, 112.63634),
                        new LatLng(-7.95253, 112.63654), new LatLng(-7.95263, 112.63707), new LatLng(-7.95275, 112.63781),
                        new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.94753, 112.64012),new LatLng(-7.94546, 112.64075),
                        new LatLng(-7.94315, 112.64165),new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211),new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606));
            }

            public void landungsari_ukcw() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),new LatLng(-7.97473, 112.62563));
            }

            public void arjosari_ukcw() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685),
                        new LatLng(-7.97596, 112.62686), new LatLng(-7.97571, 112.62653), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97548, 112.62623), new LatLng(-7.97556, 112.62616), new LatLng(-7.97558, 112.62602),
                        new LatLng(-7.97548, 112.62592), new LatLng(-7.97534, 112.62594),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97473, 112.62563));
            }

            public void gadang_ukcw() {//gl-adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97574, 112.62056), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.6214),
                        new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97473, 112.62563));
            }

            public void landungsari_univ_katolik_widya() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.9432, 112.61025),
                        new LatLng(-7.9434, 112.61015), new LatLng(-7.94681, 112.60893), new LatLng(-7.94698, 112.60887),
                        new LatLng(-7.9471, 112.60883), new LatLng(-7.94738, 112.60881), new LatLng(-7.94797, 112.60875),
                        new LatLng(-7.94815, 112.60876), new LatLng(-7.95068, 112.60889), new LatLng(-7.95092, 112.60891),
                        new LatLng(-7.95141, 112.60899), new LatLng(-7.95147, 112.60901), new LatLng(-7.95208, 112.60919),
                        new LatLng(-7.95227, 112.60928), new LatLng(-7.95291, 112.6098), new LatLng(-7.95358, 112.61029),
                        new LatLng(-7.9543, 112.6106), new LatLng(-7.95665, 112.61275), new LatLng(-7.95679, 112.61286),
                        new LatLng(-7.95755, 112.61347), new LatLng(-7.95777, 112.61353), new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.96094, 112.61364), new LatLng(-7.96175, 112.61339), new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96393, 112.61352),new LatLng(-7.9639, 112.6138), new LatLng(-7.96391, 112.61405), new LatLng(-7.96394, 112.61426),
                        new LatLng(-7.96398, 112.61439), new LatLng(-7.96424, 112.61505), new LatLng(-7.96437, 112.61525),
                        new LatLng(-7.96447, 112.61538), new LatLng(-7.9646, 112.61573), new LatLng(-7.96467, 112.61587),
                        new LatLng(-7.96502, 112.61637), new LatLng(-7.96553, 112.61605), new LatLng(-7.9661, 112.61573),
                        new LatLng(-7.96631, 112.61565), new LatLng(-7.96704, 112.61548), new LatLng(-7.96747, 112.6154),
                        new LatLng(-7.96751, 112.61555), new LatLng(-7.96803, 112.61672), new LatLng(-7.96844, 112.61726),
                        new LatLng(-7.96865, 112.61746));
            }

            public void arjosari_univ_katolik_widya() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946),new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685),
                        new LatLng(-7.97596, 112.62686), new LatLng(-7.97571, 112.62653), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97548, 112.62623), new LatLng(-7.97556, 112.62616), new LatLng(-7.97558, 112.62602),
                        new LatLng(-7.97548, 112.62592), new LatLng(-7.97534, 112.62594),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97473, 112.62563),
                        new LatLng(-7.97403, 112.62488), new LatLng(-7.9734, 112.62405),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.97269, 112.62255), new LatLng(-7.97245, 112.62202),
                        new LatLng(-7.97249, 112.62186), new LatLng(-7.97249, 112.6217), new LatLng(-7.9728, 112.62156),
                        new LatLng(-7.97453, 112.62107), new LatLng(-7.97448, 112.62088),new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.6214),
                        new LatLng(-7.97196, 112.62158), new LatLng(-7.97185, 112.62166),
                        new LatLng(-7.97139, 112.62088), new LatLng(-7.97055, 112.61961), new LatLng(-7.96911, 112.61795),
                        new LatLng(-7.96889, 112.61772), new LatLng(-7.96865, 112.61746));
            }

            public void gadang_univ_katolik_widya() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97574, 112.62056), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.6214),
                        new LatLng(-7.97197, 112.62158), new LatLng(-7.97187, 112.62164),
                        new LatLng(-7.97139, 112.62088), new LatLng(-7.97055, 112.61961), new LatLng(-7.96911, 112.61795),
                        new LatLng(-7.96889, 112.61772), new LatLng(-7.96865, 112.61746));
            }

            public void landungsari_ikip_budi_utomo() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62937),
                        new LatLng(-7.97647, 112.62937), new LatLng(-7.97647, 112.629), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701));
            }

            public void gadang_ikip_budi_utomo() {//ldg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97883, 112.62544), new LatLng(-7.97757, 112.62615));
            }

            public void arjosari_ikip_budi_utomo() {//at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701), new LatLng(-7.97787, 112.62652));
            }

            public void arjosari_ipm() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211), new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606),
                        new LatLng(-7.93915, 112.63577),new LatLng(-7.93919, 112.63531), new LatLng(-7.93916, 112.6351), new LatLng(-7.93888, 112.63425),
                        new LatLng(-7.93883, 112.63398), new LatLng(-7.93884, 112.63374), new LatLng(-7.93896, 112.6334),new LatLng(-7.93904, 112.63332),
                        new LatLng(-7.93928, 112.63302), new LatLng(-7.93934, 112.63288), new LatLng(-7.93937, 112.6327),
                        new LatLng(-7.93946, 112.63214), new LatLng(-7.93943, 112.63165), new LatLng(-7.93937, 112.63144),
                        new LatLng(-7.93923, 112.63105),new LatLng(-7.93763, 112.62768), new LatLng(-7.93715, 112.62687), new LatLng(-7.93701, 112.62648),
                        new LatLng(-7.93705, 112.62641), new LatLng(-7.93705, 112.62634), new LatLng(-7.93731, 112.62615),new LatLng(-7.9384, 112.6256),
                        new LatLng(-7.93953, 112.625), new LatLng(-7.93965, 112.62488), new LatLng(-7.94037, 112.62404),
                        new LatLng(-7.94153, 112.62272));
            }

            public void gadang_ipm() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387), new LatLng(-7.96451, 112.63496),
                        new LatLng(-7.96256, 112.63141), new LatLng(-7.96205, 112.63055), new LatLng(-7.96166, 112.63087),
                        new LatLng(-7.96116, 112.63129), new LatLng(-7.96076, 112.63151), new LatLng(-7.96054, 112.63158),
                        new LatLng(-7.95964, 112.63205), new LatLng(-7.95955, 112.63189), new LatLng(-7.95919, 112.63141),
                        new LatLng(-7.95893, 112.63114), new LatLng(-7.95832, 112.63148), new LatLng(-7.95806, 112.6316),
                        new LatLng(-7.95789, 112.63168), new LatLng(-7.95768, 112.63176), new LatLng(-7.95763, 112.63167),
                        new LatLng(-7.95695, 112.63158), new LatLng(-7.95682, 112.63159), new LatLng(-7.95664, 112.63159),
                        new LatLng(-7.95656, 112.63153), new LatLng(-7.95608, 112.63149), new LatLng(-7.95558, 112.6315),
                        new LatLng(-7.95545, 112.63152), new LatLng(-7.9545, 112.63162), new LatLng(-7.95426, 112.63166),
                        new LatLng(-7.95288, 112.63178), new LatLng(-7.95264, 112.63179), new LatLng(-7.95197, 112.6317),
                        new LatLng(-7.95189, 112.63175), new LatLng(-7.95124, 112.63161), new LatLng(-7.95093, 112.63165),
                        new LatLng(-7.95085, 112.63167), new LatLng(-7.95037, 112.63),

                        new LatLng(-7.95005, 112.62944), new LatLng(-7.94902, 112.62736), new LatLng(-7.94628, 112.62227),
                        new LatLng(-7.94619, 112.62203), new LatLng(-7.94594, 112.62162), new LatLng(-7.94573, 112.62132),
                        new LatLng(-7.94527, 112.62061), new LatLng(-7.94497, 112.6201), new LatLng(-7.9446, 112.61948),
                        new LatLng(-7.9445, 112.61935), new LatLng(-7.94153, 112.62272));
            }

            public void landungsari_ipm() {//ckl-abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),
                        new LatLng(-7.94934, 112.61572), new LatLng(-7.94575, 112.61828), new LatLng(-7.94451, 112.61934), new LatLng(-7.94153, 112.62272));
            }

            public void landungsari_pum() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.93725, 112.60621));
            }

            public void gadang_pum() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338),
                        new LatLng(-7.97044, 112.6134),new LatLng(-7.965, 112.61357),new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96175, 112.61339),new LatLng(-7.96094, 112.61364),new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.95777, 112.61353),new LatLng(-7.95755, 112.61347),new LatLng(-7.95679, 112.61286),new LatLng(-7.95665, 112.61275),new LatLng(-7.9543, 112.6106),
                        new LatLng(-7.95358, 112.61029),new LatLng(-7.95291, 112.6098),new LatLng(-7.95227, 112.60928),
                        new LatLng(-7.95208, 112.60919),new LatLng(-7.95147, 112.60901),new LatLng(-7.95141, 112.60899),
                        new LatLng(-7.95092, 112.60891),new LatLng(-7.95068, 112.60889),new LatLng(-7.94815, 112.60876),
                        new LatLng(-7.94797, 112.60875),new LatLng(-7.94738, 112.60881),new LatLng(-7.9471, 112.60883),
                        new LatLng(-7.94698, 112.60887),new LatLng(-7.94681, 112.60893),new LatLng(-7.9434, 112.61015),
                        new LatLng(-7.9432, 112.61025),new LatLng(-7.94313, 112.61032),new LatLng(-7.93963, 112.60786),new LatLng(-7.9377, 112.60652));
            }

            public void arjosari_pum() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95461, 112.62051), new LatLng(-7.95291, 112.61903),
                        new LatLng(-7.94996, 112.61545), new LatLng(-7.94985, 112.61536), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94616, 112.61238), new LatLng(-7.94451, 112.61131),
                        new LatLng(-7.94214, 112.60957), new LatLng(-7.93964, 112.60786), new LatLng(-7.93729, 112.60623));
            }

            public void arjosari_poltekes_kemenkes() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486));
            }

            public void landungsari_poltekes_kemenkes() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486));
            }

            public void gadang_poltekes_kemenkes() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97574, 112.62056), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.6214),
                        new LatLng(-7.97197, 112.62158), new LatLng(-7.97185, 112.62166), new LatLng(-7.9684, 112.62364), new LatLng(-7.96834, 112.62375),
                        new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486));
            }

            public void gadang_poltekes_soepraon() {//ga
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),new LatLng(-8.00428, 112.61799),new LatLng(-8.00284, 112.61773),new LatLng(-8.00117, 112.61788),new LatLng(-7.99094, 112.62141));
            }

            public void arjosari_poltekes_soepraon() {//ga
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97874, 112.63257), new LatLng(-7.9788, 112.6325), new LatLng(-7.97912, 112.63142),
                        new LatLng(-7.97936, 112.63118), new LatLng(-7.98025, 112.63089), new LatLng(-7.98046, 112.63066),
                        new LatLng(-7.98066, 112.6306), new LatLng(-7.98159, 112.63041), new LatLng(-7.98187, 112.63168),
                        new LatLng(-7.9836, 112.63133), new LatLng(-7.98522, 112.63093), new LatLng(-7.98739, 112.63043),
                        new LatLng(-7.98735, 112.63031), new LatLng(-7.98822, 112.62995), new LatLng(-7.98859, 112.62968),
                        new LatLng(-7.9886, 112.62962), new LatLng(-7.98831, 112.62817), new LatLng(-7.98785, 112.62588),
                        new LatLng(-7.98751, 112.62508), new LatLng(-7.98654, 112.62354), new LatLng(-7.98812, 112.62223),
                        new LatLng(-7.98876, 112.62204), new LatLng(-7.99094, 112.62141));
            }

            public void landungsari_poltekes_soepraon() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.9432, 112.61025),
                        new LatLng(-7.9434, 112.61015), new LatLng(-7.94681, 112.60893), new LatLng(-7.94698, 112.60887),
                        new LatLng(-7.9471, 112.60883), new LatLng(-7.94738, 112.60881), new LatLng(-7.94797, 112.60875),
                        new LatLng(-7.94815, 112.60876), new LatLng(-7.95068, 112.60889), new LatLng(-7.95092, 112.60891),
                        new LatLng(-7.95141, 112.60899), new LatLng(-7.95147, 112.60901), new LatLng(-7.95208, 112.60919),
                        new LatLng(-7.95227, 112.60928), new LatLng(-7.95291, 112.6098), new LatLng(-7.95358, 112.61029),
                        new LatLng(-7.9543, 112.6106), new LatLng(-7.95665, 112.61275), new LatLng(-7.95679, 112.61286),
                        new LatLng(-7.95755, 112.61347), new LatLng(-7.95777, 112.61353), new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.96094, 112.61364), new LatLng(-7.96175, 112.61339), new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.965, 112.61357), new LatLng(-7.97044, 112.6134), new LatLng(-7.97175, 112.61322),
                        new LatLng(-7.97196, 112.61329), new LatLng(-7.97315, 112.61313), new LatLng(-7.97374, 112.61304),
                        new LatLng(-7.97684, 112.61252), new LatLng(-7.97749, 112.61256), new LatLng(-7.97768, 112.61263),
                        new LatLng(-7.97829, 112.61291), new LatLng(-7.98006, 112.61412), new LatLng(-7.9814, 112.6147),
                        new LatLng(-7.98163, 112.61472), new LatLng(-7.98211, 112.61466), new LatLng(-7.98355, 112.61431),
                        new LatLng(-7.9834, 112.61501), new LatLng(-7.98334, 112.6156), new LatLng(-7.98354, 112.61884),
                        new LatLng(-7.98361, 112.6199), new LatLng(-7.98354, 112.62057), new LatLng(-7.98327, 112.62164),
                        new LatLng(-7.98323, 112.62204), new LatLng(-7.98359, 112.62444), new LatLng(-7.98407, 112.62611),
                        new LatLng(-7.98572, 112.62417), new LatLng(-7.98804, 112.62229), new LatLng(-7.98843, 112.62211),
                        new LatLng(-7.98892, 112.62203), new LatLng(-7.98998, 112.62171), new LatLng(-7.99094, 112.62141));
            }
            public void arjosari_akademi_pariwisata_hotel() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.93096, 112.65042), new LatLng(-7.9317, 112.6501),new LatLng(-7.93244, 112.64987), new LatLng(-7.93363, 112.64972), new LatLng(-7.93378, 112.64972));
            }

            public void landungsari_akademi_pariwisata_dan_hotel() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603),new LatLng(-7.97526, 112.62614),new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624),new LatLng(-7.97548, 112.62623),new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653),new LatLng(-7.97596, 112.62686),new LatLng(-7.97593, 112.62694),new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701),new LatLng(-7.97623, 112.62747),new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773),new LatLng(-7.97642, 112.62788),new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987),new LatLng(-7.97647, 112.63007),new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156),new LatLng(-7.97698, 112.63346),new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348),new LatLng(-7.97758, 112.63361),new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431),new LatLng(-7.97755, 112.63452),new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683),new LatLng(-7.97747, 112.63683),new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788),new LatLng(-7.97347, 112.63856),new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833),new LatLng(-7.96759, 112.6383),new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873),new LatLng(-7.96694, 112.63851),new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821),new LatLng(-7.96598, 112.63761),new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549),new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96226, 112.63602),new LatLng(-7.96185, 112.63625),new LatLng(-7.96035, 112.63691),new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.95165, 112.63919),new LatLng(-7.94929, 112.63968),new LatLng(-7.94887, 112.63974),
                        new LatLng(-7.94798, 112.63995),new LatLng(-7.94734, 112.64007),
                        new LatLng(-7.94477, 112.64083),new LatLng(-7.94178, 112.64211),new LatLng(-7.94067, 112.64265),new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454),new LatLng(-7.94375, 112.64821),new LatLng(-7.94279, 112.64862),
                        new LatLng(-7.94129, 112.64929),new LatLng(-7.93914, 112.64973),
                        new LatLng(-7.93642, 112.64975),new LatLng(-7.93609, 112.64974),new LatLng(-7.93562, 112.64972),new LatLng(-7.93439, 112.64972));
            }

            public void gadang_akademi_pariwisata_hotel() {//amg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.94067, 112.64265),new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454),new LatLng(-7.94375, 112.64821),new LatLng(-7.94279, 112.64862),
                        new LatLng(-7.94129, 112.64929),new LatLng(-7.93914, 112.64973),
                        new LatLng(-7.93642, 112.64975),new LatLng(-7.93609, 112.64974),new LatLng(-7.93562, 112.64972),new LatLng(-7.93439, 112.64972));
            }

            public void gadang_akd_farmasi_putra() {//ajg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99672, 112.6297), new LatLng(-7.99288, 112.63141), new LatLng(-7.993, 112.63174),
                        new LatLng(-7.99307, 112.63193), new LatLng(-7.99352, 112.6335),
                        new LatLng(-7.99234, 112.63379), new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389),
                        new LatLng(-7.99137, 112.63408), new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442),
                        new LatLng(-7.98928, 112.63488), new LatLng(-7.98847, 112.633), new LatLng(-7.98672, 112.63372),
                        new LatLng(-7.98622, 112.63394), new LatLng(-7.986, 112.6335), new LatLng(-7.98353, 112.63424),
                        new LatLng(-7.98168, 112.63484), new LatLng(-7.98189, 112.63543), new LatLng(-7.98279, 112.63691),
                        new LatLng(-7.98295, 112.63712), new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97343, 112.63668),
                        new LatLng(-7.97341, 112.63681), new LatLng(-7.97342, 112.63714), new LatLng(-7.97342, 112.63724),new LatLng(-7.97347, 112.63819), new LatLng(-7.97348, 112.63855),
                        new LatLng(-7.9731, 112.63854), new LatLng(-7.9722, 112.63851), new LatLng(-7.97183, 112.63851),
                        new LatLng(-7.9704, 112.63845), new LatLng(-7.96923, 112.63839), new LatLng(-7.96863, 112.63833),
                        new LatLng(-7.9676, 112.63829), new LatLng(-7.96741, 112.63827), new LatLng(-7.96725, 112.63832),
                        new LatLng(-7.96713, 112.6383), new LatLng(-7.96701, 112.63839), new LatLng(-7.96696, 112.63855),
                        new LatLng(-7.96694, 112.6386), new LatLng(-7.96688, 112.63868), new LatLng(-7.96476, 112.63977),
                        new LatLng(-7.96145, 112.64175), new LatLng(-7.96099, 112.64195), new LatLng(-7.96068, 112.64122),
                        new LatLng(-7.96051, 112.64129), new LatLng(-7.95989, 112.63967));
            }

            public void arjosari_akd_farmasi_putra() {//asd
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.93096, 112.65042), new LatLng(-7.9317, 112.6501), new LatLng(-7.93244, 112.64987),
                        new LatLng(-7.93363, 112.64972), new LatLng(-7.93378, 112.64972), new LatLng(-7.93404, 112.64972),
                        new LatLng(-7.93439, 112.64972), new LatLng(-7.93562, 112.64972), new LatLng(-7.93609, 112.64974),
                        new LatLng(-7.93642, 112.64975), new LatLng(-7.93825, 112.64977), new LatLng(-7.93914, 112.64973),
                        new LatLng(-7.93948, 112.64968), new LatLng(-7.94012, 112.65045), new LatLng(-7.94107, 112.65148),
                        new LatLng(-7.94156, 112.65212), new LatLng(-7.9422, 112.65268), new LatLng(-7.94292, 112.65302),
                        new LatLng(-7.94344, 112.65332), new LatLng(-7.94373, 112.65367), new LatLng(-7.94445, 112.65485),
                        new LatLng(-7.94579, 112.65694), new LatLng(-7.9467, 112.65803), new LatLng(-7.94675, 112.65809),
                        new LatLng(-7.94749, 112.65903), new LatLng(-7.94763, 112.65914), new LatLng(-7.94791, 112.65925),
                        new LatLng(-7.94795, 112.65889), new LatLng(-7.948, 112.6587), new LatLng(-7.94814, 112.65866),
                        new LatLng(-7.94848, 112.65853), new LatLng(-7.94782, 112.65603), new LatLng(-7.94681, 112.65414),
                        new LatLng(-7.94801, 112.65363), new LatLng(-7.94901, 112.65289), new LatLng(-7.95062, 112.65208),
                        new LatLng(-7.95227, 112.65118), new LatLng(-7.95321, 112.65082), new LatLng(-7.95333, 112.65074),
                        new LatLng(-7.95339, 112.65065), new LatLng(-7.95332, 112.65017), new LatLng(-7.95254, 112.64847),
                        new LatLng(-7.95253, 112.64839), new LatLng(-7.95293, 112.64787), new LatLng(-7.95299, 112.6478),
                        new LatLng(-7.95351, 112.64757), new LatLng(-7.95356, 112.64779), new LatLng(-7.954, 112.6488),
                        new LatLng(-7.95453, 112.65003), new LatLng(-7.95481, 112.64988), new LatLng(-7.95531, 112.64966),
                        new LatLng(-7.95678, 112.64917), new LatLng(-7.95969, 112.64848), new LatLng(-7.95848, 112.64606),
                        new LatLng(-7.95839, 112.64588), new LatLng(-7.95806, 112.64543), new LatLng(-7.95784, 112.64513),
                        new LatLng(-7.95741, 112.64462), new LatLng(-7.95704, 112.6441), new LatLng(-7.9584, 112.6435),
                        new LatLng(-7.95986, 112.64263), new LatLng(-7.96018, 112.64242), new LatLng(-7.96099, 112.64195),
                        new LatLng(-7.96068, 112.64122), new LatLng(-7.96051, 112.64129), new LatLng(-7.95989, 112.63967));
            }

            public void landungsari_akd_farmasi_putra() {//adl-asd
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281), new LatLng(-7.97637, 112.62934), new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987), new LatLng(-7.97647, 112.63007), new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97698, 112.63346), new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.97758, 112.63361), new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97755, 112.63452), new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.97747, 112.63683), new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97347, 112.63856), new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.96759, 112.6383), new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96694, 112.63851), new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.96598, 112.63761), new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625), new LatLng(-7.96035, 112.63691), new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.95165, 112.63919), new LatLng(-7.94929, 112.63968), new LatLng(-7.94887, 112.63974),
                        new LatLng(-7.94798, 112.63995), new LatLng(-7.94734, 112.64007),
                        new LatLng(-7.94477, 112.64083), new LatLng(-7.94178, 112.64211), new LatLng(-7.94067, 112.64265), new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454), new LatLng(-7.94375, 112.64821), new LatLng(-7.94279, 112.64862), new LatLng(-7.94129, 112.64929), new LatLng(-7.93948, 112.64968),
                        new LatLng(-7.94012, 112.65045), new LatLng(-7.94107, 112.65148),
                        new LatLng(-7.94156, 112.65212), new LatLng(-7.9422, 112.65268), new LatLng(-7.94292, 112.65302),
                        new LatLng(-7.94344, 112.65332), new LatLng(-7.94373, 112.65367), new LatLng(-7.94445, 112.65485),
                        new LatLng(-7.94579, 112.65694), new LatLng(-7.9467, 112.65803), new LatLng(-7.94675, 112.65809),
                        new LatLng(-7.94749, 112.65903), new LatLng(-7.94763, 112.65914), new LatLng(-7.94791, 112.65925),
                        new LatLng(-7.94795, 112.65889), new LatLng(-7.948, 112.6587), new LatLng(-7.94814, 112.65866),
                        new LatLng(-7.94848, 112.65853), new LatLng(-7.94782, 112.65603), new LatLng(-7.94681, 112.65414),
                        new LatLng(-7.94801, 112.65363), new LatLng(-7.94901, 112.65289), new LatLng(-7.95062, 112.65208),
                        new LatLng(-7.95227, 112.65118), new LatLng(-7.95321, 112.65082), new LatLng(-7.95333, 112.65074),
                        new LatLng(-7.95339, 112.65065), new LatLng(-7.95332, 112.65017), new LatLng(-7.95254, 112.64847),
                        new LatLng(-7.95253, 112.64839), new LatLng(-7.95293, 112.64787), new LatLng(-7.95299, 112.6478),
                        new LatLng(-7.95351, 112.64757), new LatLng(-7.95356, 112.64779), new LatLng(-7.954, 112.6488),
                        new LatLng(-7.95453, 112.65003), new LatLng(-7.95481, 112.64988), new LatLng(-7.95531, 112.64966),
                        new LatLng(-7.95678, 112.64917), new LatLng(-7.95969, 112.64848), new LatLng(-7.95848, 112.64606),
                        new LatLng(-7.95839, 112.64588), new LatLng(-7.95806, 112.64543), new LatLng(-7.95784, 112.64513),
                        new LatLng(-7.95741, 112.64462), new LatLng(-7.95704, 112.6441), new LatLng(-7.9584, 112.6435),
                        new LatLng(-7.95986, 112.64263), new LatLng(-7.96018, 112.64242), new LatLng(-7.96099, 112.64195),
                        new LatLng(-7.96068, 112.64122), new LatLng(-7.96051, 112.64129), new LatLng(-7.95989, 112.63967));
            }

            public void gadang_akd_keperawatan_waluyo() {//ag
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338),new LatLng(-7.99352, 112.6335), new LatLng(-7.99306, 112.6319),
                        new LatLng(-7.99297, 112.63166), new LatLng(-7.99287, 112.63141),
                        new LatLng(-7.99266, 112.63122), new LatLng(-7.9925, 112.63113),
                        new LatLng(-7.99218, 112.63104),new LatLng(-7.9919, 112.63108), new LatLng(-7.99162, 112.63025), new LatLng(-7.99114, 112.62882),
                        new LatLng(-7.9909, 112.62813), new LatLng(-7.99083, 112.62801), new LatLng(-7.9907, 112.62787),
                        new LatLng(-7.98991, 112.62677),new LatLng(-7.98914, 112.6257), new LatLng(-7.98788, 112.62594), new LatLng(-7.98664, 112.62633),
                        new LatLng(-7.98599, 112.62657));
                setUpMapIfNeeded();
            }

            public void landungsari_akd_keperawatan_waluyo() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62937),
                        new LatLng(-7.97647, 112.62937), new LatLng(-7.97647, 112.629), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701),
                        new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.98047, 112.62696), new LatLng(-7.98071, 112.62726), new LatLng(-7.9809, 112.62759),
                        new LatLng(-7.98159, 112.63041), new LatLng(-7.98187, 112.63168), new LatLng(-7.9836, 112.63133),
                        new LatLng(-7.98305, 112.62923), new LatLng(-7.983, 112.62908), new LatLng(-7.98262, 112.62749),
                        new LatLng(-7.98247, 112.62693), new LatLng(-7.98409, 112.62621), new LatLng(-7.98409, 112.6261),
                        new LatLng(-7.98453, 112.62542), new LatLng(-7.98512, 112.62471), new LatLng(-7.98535, 112.6251),
                        new LatLng(-7.98545, 112.62531));
            }


            public void arjosari_akd_keperawatan_waluyo() {//ga-gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97874, 112.63257), new LatLng(-7.9788, 112.6325), new LatLng(-7.97912, 112.63142),
                        new LatLng(-7.97936, 112.63118), new LatLng(-7.98025, 112.63089), new LatLng(-7.98046, 112.63066),
                        new LatLng(-7.98066, 112.6306), new LatLng(-7.98159, 112.63041), new LatLng(-7.98187, 112.63168),
                        new LatLng(-7.9836, 112.63133),new LatLng(-7.98305, 112.62923), new LatLng(-7.983, 112.62908), new LatLng(-7.98262, 112.62749),
                        new LatLng(-7.98247, 112.62693), new LatLng(-7.98409, 112.62621), new LatLng(-7.98409, 112.6261),
                        new LatLng(-7.98453, 112.62542), new LatLng(-7.98512, 112.62471), new LatLng(-7.98535, 112.6251),
                        new LatLng(-7.98545, 112.62531));
            }

            public void landungsari_akd_wira_husada() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338));
            }

            public void arjosari_akd_wira_husada() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),

                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984),
                        new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95461, 112.62051), new LatLng(-7.95291, 112.61903),
                        new LatLng(-7.94996, 112.61545), new LatLng(-7.94985, 112.61536), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94616, 112.61238), new LatLng(-7.94451, 112.61131),
                        new LatLng(-7.94214, 112.60957), new LatLng(-7.93964, 112.60786), new LatLng(-7.93729, 112.60623),
                        new LatLng(-7.93598, 112.60533),new LatLng(-7.93316, 112.60338));
            }

            public void gadang_akd_wira_husada() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338),
                        new LatLng(-7.97044, 112.6134),new LatLng(-7.965, 112.61357),new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96175, 112.61339),new LatLng(-7.96094, 112.61364),new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.95777, 112.61353),new LatLng(-7.95755, 112.61347),new LatLng(-7.95679, 112.61286),new LatLng(-7.95665, 112.61275),new LatLng(-7.9543, 112.6106),
                        new LatLng(-7.95358, 112.61029),new LatLng(-7.95291, 112.6098),new LatLng(-7.95227, 112.60928),
                        new LatLng(-7.95208, 112.60919),new LatLng(-7.95147, 112.60901),new LatLng(-7.95141, 112.60899),
                        new LatLng(-7.95092, 112.60891),new LatLng(-7.95068, 112.60889),new LatLng(-7.94815, 112.60876),
                        new LatLng(-7.94797, 112.60875),new LatLng(-7.94738, 112.60881),new LatLng(-7.9471, 112.60883),
                        new LatLng(-7.94698, 112.60887),new LatLng(-7.94681, 112.60893),new LatLng(-7.9434, 112.61015),
                        new LatLng(-7.9432, 112.61025),new LatLng(-7.94313, 112.61032),new LatLng(-7.93963, 112.60786),new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93598, 112.60533),new LatLng(-7.93316, 112.60338));
            }

            public void arjosari_akbid_wijaya_kusuma() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95399, 112.63872));
            }

            public void landungsari_akbid_wijaya_kusuma() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281), new LatLng(-7.97637, 112.62934), new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987), new LatLng(-7.97647, 112.63007), new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97698, 112.63346), new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.97758, 112.63361), new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97755, 112.63452), new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.97747, 112.63683), new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97347, 112.63856), new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.96759, 112.6383), new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96694, 112.63851), new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.96598, 112.63761), new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96452, 112.63496), new LatLng(-7.96227, 112.63615),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96041, 112.637), new LatLng(-7.95707, 112.63804),
                        new LatLng(-7.95399, 112.63872));
            }

            public void gadang_akbid_wijaya_kusuma() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95398, 112.63863));
            }

            public void landungsari_akd_analisis_kesehatan() {//ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),
                        new LatLng(-7.94934, 112.61572), new LatLng(-7.94575, 112.61828), new LatLng(-7.94451, 112.61934),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95122, 112.63285),
                        new LatLng(-7.95158, 112.63387), new LatLng(-7.95196, 112.63461),
                        new LatLng(-7.95206, 112.63477), new LatLng(-7.9523, 112.63529), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.95257, 112.63612), new LatLng(-7.95252, 112.63634),
                        new LatLng(-7.95253, 112.63654), new LatLng(-7.95263, 112.63707), new LatLng(-7.95275, 112.63781),
                        new LatLng(-7.95297, 112.63884), new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.9736, 112.63856), new LatLng(-7.97533, 112.64104), new LatLng(-7.97546, 112.64119),
                        new LatLng(-7.97552, 112.64127), new LatLng(-7.97744, 112.64352), new LatLng(-7.97854, 112.6458),
                        new LatLng(-7.97864, 112.64621), new LatLng(-7.97884, 112.64758), new LatLng(-7.97905, 112.6493),
                        new LatLng(-7.97918, 112.64975), new LatLng(-7.97975, 112.65143), new LatLng(-7.98031, 112.65263),
                        new LatLng(-7.98088, 112.65355), new LatLng(-7.98094, 112.65368),
                        new LatLng(-7.97945, 112.65428), new LatLng(-7.97652, 112.65491), new LatLng(-7.97639, 112.65495),
                        new LatLng(-7.97536, 112.65516), new LatLng(-7.97532, 112.65518),
                        new LatLng(-7.97349, 112.65591), new LatLng(-7.97142, 112.65675), new LatLng(-7.96837, 112.65816),
                        new LatLng(-7.96745, 112.65879), new LatLng(-7.96642, 112.65965), new LatLng(-7.9663, 112.65972),
                        new LatLng(-7.96655, 112.66027), new LatLng(-7.96679, 112.66084), new LatLng(-7.96731, 112.66204),
                        new LatLng(-7.96789, 112.66172), new LatLng(-7.96928, 112.66095), new LatLng(-7.96985, 112.66062),
                        new LatLng(-7.96999, 112.66054), new LatLng(-7.97013, 112.66053),
                        new LatLng(-7.97042, 112.66055), new LatLng(-7.97059, 112.66062), new LatLng(-7.97161, 112.66132),
                        new LatLng(-7.97178, 112.66148), new LatLng(-7.97276, 112.66328),
                        new LatLng(-7.9724, 112.66352), new LatLng(-7.97289, 112.66439), new LatLng(-7.97371, 112.66589),
                        new LatLng(-7.97394, 112.66629), new LatLng(-7.9727, 112.66692),
                        new LatLng(-7.97254, 112.66702), new LatLng(-7.97227, 112.66714), new LatLng(-7.97216, 112.66723),
                        new LatLng(-7.97217, 112.66731), new LatLng(-7.97221, 112.66734), new LatLng(-7.97241, 112.66753), new LatLng(-7.97272, 112.6678),
                        new LatLng(-7.97277, 112.66789), new LatLng(-7.97273, 112.66797), new LatLng(-7.97263, 112.66802),
                        new LatLng(-7.97224, 112.66819), new LatLng(-7.97174, 112.66848), new LatLng(-7.97153, 112.66858));
            }

            public void arjosari_akd_analisis_kesehatan() {//al-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.9736, 112.63856), new LatLng(-7.97533, 112.64104), new LatLng(-7.97546, 112.64119),
                        new LatLng(-7.97552, 112.64127), new LatLng(-7.97744, 112.64352), new LatLng(-7.97854, 112.6458),
                        new LatLng(-7.97864, 112.64621), new LatLng(-7.97884, 112.64758), new LatLng(-7.97905, 112.6493),
                        new LatLng(-7.97918, 112.64975), new LatLng(-7.97975, 112.65143), new LatLng(-7.98031, 112.65263),
                        new LatLng(-7.98088, 112.65355), new LatLng(-7.98094, 112.65368),
                        new LatLng(-7.97945, 112.65428), new LatLng(-7.97652, 112.65491), new LatLng(-7.97639, 112.65495),
                        new LatLng(-7.97536, 112.65516), new LatLng(-7.97532, 112.65518),
                        new LatLng(-7.97349, 112.65591), new LatLng(-7.97142, 112.65675), new LatLng(-7.96837, 112.65816),
                        new LatLng(-7.96745, 112.65879), new LatLng(-7.96642, 112.65965), new LatLng(-7.9663, 112.65972),
                        new LatLng(-7.96655, 112.66027), new LatLng(-7.96679, 112.66084), new LatLng(-7.96731, 112.66204),
                        new LatLng(-7.96789, 112.66172), new LatLng(-7.96928, 112.66095), new LatLng(-7.96985, 112.66062),
                        new LatLng(-7.96999, 112.66054), new LatLng(-7.97013, 112.66053),
                        new LatLng(-7.97042, 112.66055), new LatLng(-7.97059, 112.66062), new LatLng(-7.97161, 112.66132),
                        new LatLng(-7.97178, 112.66148), new LatLng(-7.97276, 112.66328),
                        new LatLng(-7.9724, 112.66352), new LatLng(-7.97289, 112.66439), new LatLng(-7.97371, 112.66589),
                        new LatLng(-7.97394, 112.66629), new LatLng(-7.9727, 112.66692),
                        new LatLng(-7.97254, 112.66702), new LatLng(-7.97227, 112.66714), new LatLng(-7.97216, 112.66723),
                        new LatLng(-7.97217, 112.66731), new LatLng(-7.97221, 112.66734), new LatLng(-7.97241, 112.66753), new LatLng(-7.97272, 112.6678),
                        new LatLng(-7.97277, 112.66789), new LatLng(-7.97273, 112.66797), new LatLng(-7.97263, 112.66802),
                        new LatLng(-7.97224, 112.66819), new LatLng(-7.97174, 112.66848), new LatLng(-7.97142, 112.66863));
            }

            public void gadang_akd_analisis_kesehatan() {//ajg-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99672, 112.6297), new LatLng(-7.99288, 112.63141), new LatLng(-7.993, 112.63174),
                        new LatLng(-7.99307, 112.63193), new LatLng(-7.99352, 112.6335),
                        new LatLng(-7.99234, 112.63379), new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389),
                        new LatLng(-7.99137, 112.63408), new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442),
                        new LatLng(-7.98928, 112.63488), new LatLng(-7.98847, 112.633), new LatLng(-7.98672, 112.63372),
                        new LatLng(-7.98622, 112.63394), new LatLng(-7.986, 112.6335), new LatLng(-7.98353, 112.63424),
                        new LatLng(-7.98168, 112.63484), new LatLng(-7.98189, 112.63543), new LatLng(-7.98279, 112.63691),
                        new LatLng(-7.98295, 112.63712), new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97343, 112.63668),
                        new LatLng(-7.97341, 112.63681), new LatLng(-7.97342, 112.63714), new LatLng(-7.97342, 112.63724),new LatLng(-7.97347, 112.63819), new LatLng(-7.97348, 112.63855),
                        new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.9736, 112.63856), new LatLng(-7.97533, 112.64104), new LatLng(-7.97546, 112.64119),
                        new LatLng(-7.97552, 112.64127), new LatLng(-7.97744, 112.64352), new LatLng(-7.97854, 112.6458),
                        new LatLng(-7.97864, 112.64621), new LatLng(-7.97884, 112.64758), new LatLng(-7.97905, 112.6493),
                        new LatLng(-7.97918, 112.64975), new LatLng(-7.97975, 112.65143), new LatLng(-7.98031, 112.65263),
                        new LatLng(-7.98088, 112.65355), new LatLng(-7.98094, 112.65368),
                        new LatLng(-7.97945, 112.65428), new LatLng(-7.97652, 112.65491), new LatLng(-7.97639, 112.65495),
                        new LatLng(-7.97536, 112.65516), new LatLng(-7.97532, 112.65518),
                        new LatLng(-7.97349, 112.65591), new LatLng(-7.97142, 112.65675), new LatLng(-7.96837, 112.65816),
                        new LatLng(-7.96745, 112.65879), new LatLng(-7.96642, 112.65965), new LatLng(-7.9663, 112.65972),
                        new LatLng(-7.96655, 112.66027), new LatLng(-7.96679, 112.66084), new LatLng(-7.96731, 112.66204),
                        new LatLng(-7.96789, 112.66172), new LatLng(-7.96928, 112.66095), new LatLng(-7.96985, 112.66062),
                        new LatLng(-7.96999, 112.66054), new LatLng(-7.97013, 112.66053),
                        new LatLng(-7.97042, 112.66055), new LatLng(-7.97059, 112.66062), new LatLng(-7.97161, 112.66132),
                        new LatLng(-7.97178, 112.66148), new LatLng(-7.97276, 112.66328),
                        new LatLng(-7.9724, 112.66352), new LatLng(-7.97289, 112.66439), new LatLng(-7.97371, 112.66589),
                        new LatLng(-7.97394, 112.66629), new LatLng(-7.9727, 112.66692),
                        new LatLng(-7.97254, 112.66702), new LatLng(-7.97227, 112.66714), new LatLng(-7.97216, 112.66723),
                        new LatLng(-7.97217, 112.66731), new LatLng(-7.97221, 112.66734), new LatLng(-7.97241, 112.66753), new LatLng(-7.97272, 112.6678),
                        new LatLng(-7.97277, 112.66789), new LatLng(-7.97273, 112.66797), new LatLng(-7.97263, 112.66802),
                        new LatLng(-7.97224, 112.66819), new LatLng(-7.97174, 112.66848), new LatLng(-7.97142, 112.66863));
            }

            public void arjosari_balai_diklat() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489));
            }

            public void landungsari_balai_diklat() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603),new LatLng(-7.97526, 112.62614),new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624),new LatLng(-7.97548, 112.62623),new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653),new LatLng(-7.97596, 112.62686),new LatLng(-7.97593, 112.62694),new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701),new LatLng(-7.97623, 112.62747),new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773),new LatLng(-7.97642, 112.62788),new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987),new LatLng(-7.97647, 112.63007),new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156),new LatLng(-7.97698, 112.63346),new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348),new LatLng(-7.97758, 112.63361),new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431),new LatLng(-7.97755, 112.63452),new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683),new LatLng(-7.97747, 112.63683),new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788),new LatLng(-7.97347, 112.63856),new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833),new LatLng(-7.96759, 112.6383),new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873),new LatLng(-7.96694, 112.63851),new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821),new LatLng(-7.96598, 112.63761),new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549),new LatLng(-7.96452, 112.63496),new LatLng(-7.96227, 112.63615),
                        new LatLng(-7.96074, 112.63684),new LatLng(-7.96041, 112.637),new LatLng(-7.95707, 112.63804),
                        new LatLng(-7.95505, 112.6385),new LatLng(-7.94753, 112.64012),new LatLng(-7.94546, 112.64075),
                        new LatLng(-7.94315, 112.64165),new LatLng(-7.94178, 112.64211),new LatLng(-7.94056, 112.64272),
                        new LatLng(-7.93576, 112.64516),new LatLng(-7.9335, 112.64642),
                        new LatLng(-7.9331, 112.64665),new LatLng(-7.93071, 112.64798),new LatLng(-7.92912, 112.64884),new LatLng(-7.92941, 112.64933));
            }

            public void gadang_balai_diklat() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.94055, 112.64272), new LatLng(-7.93818, 112.64384), new LatLng(-7.93351, 112.64641),
                        new LatLng(-7.93321, 112.64659), new LatLng(-7.93235, 112.64705), new LatLng(-7.9307, 112.64799),
                        new LatLng(-7.92912, 112.64884), new LatLng(-7.92938, 112.64927));
            }

            public void landungsari_atlas_nusantara() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603),new LatLng(-7.97526, 112.62614),new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624),new LatLng(-7.97548, 112.62623),new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653),new LatLng(-7.97596, 112.62686),new LatLng(-7.97593, 112.62694),new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701),new LatLng(-7.97623, 112.62747),new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773),new LatLng(-7.97642, 112.62788),new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987),new LatLng(-7.97647, 112.63007),new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156),new LatLng(-7.97698, 112.63346),new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348),new LatLng(-7.97758, 112.63361),new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431),new LatLng(-7.97755, 112.63452),new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683),new LatLng(-7.97747, 112.63683),new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788),new LatLng(-7.97347, 112.63856),new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833),new LatLng(-7.96759, 112.6383),new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873),new LatLng(-7.96694, 112.63851),new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821),new LatLng(-7.96598, 112.63761),new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549),new LatLng(-7.96452, 112.63496),new LatLng(-7.96227, 112.63615),
                        new LatLng(-7.96074, 112.63684),new LatLng(-7.96041, 112.637),new LatLng(-7.95707, 112.63804),
                        new LatLng(-7.95505, 112.6385),new LatLng(-7.94753, 112.64012),new LatLng(-7.94546, 112.64075),
                        new LatLng(-7.94315, 112.64165),new LatLng(-7.94178, 112.64211),new LatLng(-7.94056, 112.64272),
                        new LatLng(-7.93576, 112.64516),new LatLng(-7.9335, 112.64642),
                        new LatLng(-7.9331, 112.64665),new LatLng(-7.93071, 112.64798),new LatLng(-7.92912, 112.64884),new LatLng(-7.92941, 112.64933),
                        new LatLng(-7.93018, 112.65072), new LatLng(-7.93135, 112.65365), new LatLng(-7.93268, 112.65671),
                        new LatLng(-7.93281, 112.65716), new LatLng(-7.9329, 112.65758), new LatLng(-7.93303, 112.65954));
            }

            public void gadang_atlas_nusantara() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.94055, 112.64272), new LatLng(-7.93818, 112.64384), new LatLng(-7.93351, 112.64641),
                        new LatLng(-7.93321, 112.64659), new LatLng(-7.93235, 112.64705), new LatLng(-7.9307, 112.64799),
                        new LatLng(-7.92912, 112.64884), new LatLng(-7.92938, 112.64927),
                        new LatLng(-7.93018, 112.65072), new LatLng(-7.93135, 112.65365), new LatLng(-7.93268, 112.65671),
                        new LatLng(-7.93281, 112.65716), new LatLng(-7.9329, 112.65758), new LatLng(-7.93303, 112.65954));
            }

            public void landungsari_stt() {//ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786),new LatLng(-7.94214, 112.60957),
                        new LatLng(-7.94279, 112.61008), new LatLng(-7.94156, 112.61125), new LatLng(-7.94114, 112.61085),
                        new LatLng(-7.94043, 112.61145), new LatLng(-7.9402, 112.61159), new LatLng(-7.93978, 112.61178),
                        new LatLng(-7.93926, 112.61185), new LatLng(-7.93861, 112.61188),
                        new LatLng(-7.93812, 112.61182), new LatLng(-7.93787, 112.61176), new LatLng(-7.93745, 112.61152),
                        new LatLng(-7.9373, 112.61152), new LatLng(-7.93706, 112.61163), new LatLng(-7.93664, 112.61186),
                        new LatLng(-7.93625, 112.61206), new LatLng(-7.93555, 112.61242), new LatLng(-7.93532, 112.6126),
                        new LatLng(-7.93494, 112.61313), new LatLng(-7.93482, 112.61315), new LatLng(-7.9345, 112.61316),
                        new LatLng(-7.93387, 112.61321), new LatLng(-7.93303, 112.61329), new LatLng(-7.93312, 112.61338),
                        new LatLng(-7.93447, 112.61524), new LatLng(-7.93479, 112.61579),new LatLng(-7.93547, 112.61723));
            }

            public void arjosari_stt() {//abg-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211), new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606),
                        new LatLng(-7.93915, 112.63577),new LatLng(-7.93919, 112.63531), new LatLng(-7.93916, 112.6351), new LatLng(-7.93888, 112.63425),
                        new LatLng(-7.93883, 112.63398), new LatLng(-7.93884, 112.63374), new LatLng(-7.93896, 112.6334),new LatLng(-7.93904, 112.63332),
                        new LatLng(-7.93928, 112.63302), new LatLng(-7.93934, 112.63288), new LatLng(-7.93937, 112.6327),
                        new LatLng(-7.93946, 112.63214), new LatLng(-7.93943, 112.63165), new LatLng(-7.93937, 112.63144),
                        new LatLng(-7.93923, 112.63105),new LatLng(-7.93763, 112.62768), new LatLng(-7.93715, 112.62687), new LatLng(-7.93701, 112.62648),
                        new LatLng(-7.93705, 112.62641), new LatLng(-7.93705, 112.62634), new LatLng(-7.93731, 112.62615),new LatLng(-7.9384, 112.6256),
                        new LatLng(-7.93953, 112.625),new LatLng(-7.93944, 112.62487),new LatLng(-7.93905, 112.62409),
                        new LatLng(-7.93844, 112.62292),new LatLng(-7.93827, 112.62225),new LatLng(-7.93782, 112.62118),
                        new LatLng(-7.93764, 112.62085),new LatLng(-7.9371, 112.62005),new LatLng(-7.93691, 112.61976),
                        new LatLng(-7.9367, 112.61927),new LatLng(-7.93631, 112.61865),new LatLng(-7.93631, 112.61865),
                        new LatLng(-7.93611, 112.61829),new LatLng(-7.93547, 112.61723));
            }

            public void gadang_stt() {//abg-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387), new LatLng(-7.96451, 112.63496),
                        new LatLng(-7.96256, 112.63141), new LatLng(-7.96205, 112.63055), new LatLng(-7.96166, 112.63087),
                        new LatLng(-7.96116, 112.63129), new LatLng(-7.96076, 112.63151), new LatLng(-7.96054, 112.63158),
                        new LatLng(-7.95964, 112.63205), new LatLng(-7.95955, 112.63189), new LatLng(-7.95919, 112.63141),
                        new LatLng(-7.95893, 112.63114), new LatLng(-7.95832, 112.63148), new LatLng(-7.95806, 112.6316),
                        new LatLng(-7.95789, 112.63168), new LatLng(-7.95768, 112.63176), new LatLng(-7.95763, 112.63167),
                        new LatLng(-7.95695, 112.63158), new LatLng(-7.95682, 112.63159), new LatLng(-7.95664, 112.63159),
                        new LatLng(-7.95656, 112.63153), new LatLng(-7.95608, 112.63149), new LatLng(-7.95558, 112.6315),
                        new LatLng(-7.95545, 112.63152), new LatLng(-7.9545, 112.63162), new LatLng(-7.95426, 112.63166),
                        new LatLng(-7.95288, 112.63178), new LatLng(-7.95264, 112.63179), new LatLng(-7.95197, 112.6317),
                        new LatLng(-7.95189, 112.63175), new LatLng(-7.95124, 112.63161), new LatLng(-7.95093, 112.63165),
                        new LatLng(-7.95085, 112.63167), new LatLng(-7.95037, 112.63),
                        new LatLng(-7.95005, 112.62944), new LatLng(-7.94902, 112.62736), new LatLng(-7.94628, 112.62227),
                        new LatLng(-7.94619, 112.62203), new LatLng(-7.94594, 112.62162), new LatLng(-7.94573, 112.62132),
                        new LatLng(-7.94527, 112.62061), new LatLng(-7.94497, 112.6201), new LatLng(-7.9446, 112.61948),
                        new LatLng(-7.9445, 112.61935), new LatLng(-7.94153, 112.62272), new LatLng(-7.94037, 112.62404),
                        new LatLng(-7.93965, 112.62488), new LatLng(-7.93953, 112.625),new LatLng(-7.93944, 112.62487),new LatLng(-7.93905, 112.62409),
                        new LatLng(-7.93844, 112.62292),new LatLng(-7.93827, 112.62225),new LatLng(-7.93782, 112.62118),
                        new LatLng(-7.93764, 112.62085),new LatLng(-7.9371, 112.62005),new LatLng(-7.93691, 112.61976),
                        new LatLng(-7.9367, 112.61927),new LatLng(-7.93631, 112.61865),new LatLng(-7.93631, 112.61865),
                        new LatLng(-7.93611, 112.61829),new LatLng(-7.93547, 112.61723));
            }

            public void landungsari_stiba() {//ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786),new LatLng(-7.94214, 112.60957),
                        new LatLng(-7.94279, 112.61008), new LatLng(-7.94156, 112.61125), new LatLng(-7.94114, 112.61085),
                        new LatLng(-7.94043, 112.61145), new LatLng(-7.9402, 112.61159), new LatLng(-7.93978, 112.61178),
                        new LatLng(-7.93926, 112.61185), new LatLng(-7.93861, 112.61188),
                        new LatLng(-7.93812, 112.61182), new LatLng(-7.93787, 112.61176), new LatLng(-7.93745, 112.61152),
                        new LatLng(-7.9373, 112.61152), new LatLng(-7.93706, 112.61163), new LatLng(-7.93664, 112.61186),
                        new LatLng(-7.93625, 112.61206), new LatLng(-7.93555, 112.61242), new LatLng(-7.93532, 112.6126),
                        new LatLng(-7.93494, 112.61313), new LatLng(-7.93482, 112.61315), new LatLng(-7.9345, 112.61316),
                        new LatLng(-7.93387, 112.61321), new LatLng(-7.93303, 112.61329), new LatLng(-7.93312, 112.61338),
                        new LatLng(-7.93447, 112.61524), new LatLng(-7.93479, 112.61579),new LatLng(-7.93547, 112.61723),new LatLng(-7.93611, 112.61829),
                        new LatLng(-7.93631, 112.61865), new LatLng(-7.93631, 112.61865), new LatLng(-7.9367, 112.61927),
                        new LatLng(-7.93691, 112.61976), new LatLng(-7.9371, 112.62005),new LatLng(-7.93764, 112.62085), new LatLng(-7.93782, 112.62118),
                        new LatLng(-7.93827, 112.62225), new LatLng(-7.93844, 112.62292), new LatLng(-7.93905, 112.62409),
                        new LatLng(-7.93944, 112.62487), new LatLng(-7.93953, 112.625), new LatLng(-7.93965, 112.62488),
                        new LatLng(-7.94037, 112.62404), new LatLng(-7.94153, 112.62272), new LatLng(-7.94461, 112.61948),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95122, 112.63285),
                        new LatLng(-7.95158, 112.63387), new LatLng(-7.95196, 112.63461),
                        new LatLng(-7.95206, 112.63477), new LatLng(-7.9523, 112.63529), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.95257, 112.63612), new LatLng(-7.95252, 112.63634),
                        new LatLng(-7.95253, 112.63654), new LatLng(-7.95263, 112.63707), new LatLng(-7.95275, 112.63781),
                        new LatLng(-7.95297, 112.63884), new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.9736, 112.63856), new LatLng(-7.97533, 112.64104), new LatLng(-7.97546, 112.64119),
                        new LatLng(-7.97552, 112.64127), new LatLng(-7.97744, 112.64352), new LatLng(-7.97854, 112.6458),
                        new LatLng(-7.97864, 112.64621), new LatLng(-7.97884, 112.64758), new LatLng(-7.97905, 112.6493),
                        new LatLng(-7.97918, 112.64975), new LatLng(-7.97975, 112.65143), new LatLng(-7.98031, 112.65263),
                        new LatLng(-7.98088, 112.65355), new LatLng(-7.98094, 112.65368),
                        new LatLng(-7.97945, 112.65428), new LatLng(-7.97652, 112.65491), new LatLng(-7.97639, 112.65495),
                        new LatLng(-7.97536, 112.65516), new LatLng(-7.97532, 112.65518),
                        new LatLng(-7.97349, 112.65591), new LatLng(-7.97142, 112.65675), new LatLng(-7.96837, 112.65816),
                        new LatLng(-7.96745, 112.65879), new LatLng(-7.96642, 112.65965), new LatLng(-7.9663, 112.65972),
                        new LatLng(-7.96655, 112.66027), new LatLng(-7.96679, 112.66084), new LatLng(-7.96731, 112.66204),
                        new LatLng(-7.96789, 112.66172), new LatLng(-7.96928, 112.66095), new LatLng(-7.96985, 112.66062),
                        new LatLng(-7.96999, 112.66054), new LatLng(-7.97013, 112.66053),
                        new LatLng(-7.97042, 112.66055), new LatLng(-7.97059, 112.66062), new LatLng(-7.97161, 112.66132),
                        new LatLng(-7.97178, 112.66148), new LatLng(-7.97276, 112.66328),
                        new LatLng(-7.9724, 112.66352), new LatLng(-7.97289, 112.66439), new LatLng(-7.97371, 112.66589),
                        new LatLng(-7.97394, 112.66629), new LatLng(-7.9727, 112.66692),
                        new LatLng(-7.97254, 112.66702), new LatLng(-7.97227, 112.66714), new LatLng(-7.97216, 112.66723),
                        new LatLng(-7.97217, 112.66731), new LatLng(-7.97221, 112.66734), new LatLng(-7.97241, 112.66753), new LatLng(-7.97272, 112.6678),
                        new LatLng(-7.97277, 112.66789), new LatLng(-7.97273, 112.66797), new LatLng(-7.97263, 112.66802),
                        new LatLng(-7.97224, 112.66819), new LatLng(-7.97174, 112.66848), new LatLng(-7.97111, 112.66879));
            }

            public void arjosari_stiba() {//al-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.9736, 112.63856), new LatLng(-7.97533, 112.64104), new LatLng(-7.97546, 112.64119),
                        new LatLng(-7.97552, 112.64127), new LatLng(-7.97744, 112.64352), new LatLng(-7.97854, 112.6458),
                        new LatLng(-7.97864, 112.64621), new LatLng(-7.97884, 112.64758), new LatLng(-7.97905, 112.6493),
                        new LatLng(-7.97918, 112.64975), new LatLng(-7.97975, 112.65143), new LatLng(-7.98031, 112.65263),
                        new LatLng(-7.98088, 112.65355), new LatLng(-7.98094, 112.65368),
                        new LatLng(-7.97945, 112.65428), new LatLng(-7.97652, 112.65491), new LatLng(-7.97639, 112.65495),
                        new LatLng(-7.97536, 112.65516), new LatLng(-7.97532, 112.65518),
                        new LatLng(-7.97349, 112.65591), new LatLng(-7.97142, 112.65675), new LatLng(-7.96837, 112.65816),
                        new LatLng(-7.96745, 112.65879), new LatLng(-7.96642, 112.65965), new LatLng(-7.9663, 112.65972),
                        new LatLng(-7.96655, 112.66027), new LatLng(-7.96679, 112.66084), new LatLng(-7.96731, 112.66204),
                        new LatLng(-7.96789, 112.66172), new LatLng(-7.96928, 112.66095), new LatLng(-7.96985, 112.66062),
                        new LatLng(-7.96999, 112.66054), new LatLng(-7.97013, 112.66053),
                        new LatLng(-7.97042, 112.66055), new LatLng(-7.97059, 112.66062), new LatLng(-7.97161, 112.66132),
                        new LatLng(-7.97178, 112.66148), new LatLng(-7.97276, 112.66328),
                        new LatLng(-7.9724, 112.66352), new LatLng(-7.97289, 112.66439), new LatLng(-7.97371, 112.66589),
                        new LatLng(-7.97394, 112.66629), new LatLng(-7.9727, 112.66692),
                        new LatLng(-7.97254, 112.66702), new LatLng(-7.97227, 112.66714), new LatLng(-7.97216, 112.66723),
                        new LatLng(-7.97217, 112.66731), new LatLng(-7.97221, 112.66734), new LatLng(-7.97241, 112.66753), new LatLng(-7.97272, 112.6678),
                        new LatLng(-7.97277, 112.66789), new LatLng(-7.97273, 112.66797), new LatLng(-7.97263, 112.66802),
                        new LatLng(-7.97224, 112.66819), new LatLng(-7.97174, 112.66848), new LatLng(-7.97111, 112.66879));
            }

            public void gadang_stiba() {//ajg-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99672, 112.6297), new LatLng(-7.99288, 112.63141), new LatLng(-7.993, 112.63174),
                        new LatLng(-7.99307, 112.63193), new LatLng(-7.99352, 112.6335),
                        new LatLng(-7.99234, 112.63379), new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389),
                        new LatLng(-7.99137, 112.63408), new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442),
                        new LatLng(-7.98928, 112.63488), new LatLng(-7.98847, 112.633), new LatLng(-7.98672, 112.63372),
                        new LatLng(-7.98622, 112.63394), new LatLng(-7.986, 112.6335), new LatLng(-7.98353, 112.63424),
                        new LatLng(-7.98168, 112.63484), new LatLng(-7.98189, 112.63543), new LatLng(-7.98279, 112.63691),
                        new LatLng(-7.98295, 112.63712), new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97343, 112.63668),
                        new LatLng(-7.97341, 112.63681), new LatLng(-7.97342, 112.63714), new LatLng(-7.97342, 112.63724),new LatLng(-7.97347, 112.63819), new LatLng(-7.97348, 112.63855),
                        new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.9736, 112.63856), new LatLng(-7.97533, 112.64104), new LatLng(-7.97546, 112.64119),
                        new LatLng(-7.97552, 112.64127), new LatLng(-7.97744, 112.64352), new LatLng(-7.97854, 112.6458),
                        new LatLng(-7.97864, 112.64621), new LatLng(-7.97884, 112.64758), new LatLng(-7.97905, 112.6493),
                        new LatLng(-7.97918, 112.64975), new LatLng(-7.97975, 112.65143), new LatLng(-7.98031, 112.65263),
                        new LatLng(-7.98088, 112.65355), new LatLng(-7.98094, 112.65368),
                        new LatLng(-7.97945, 112.65428), new LatLng(-7.97652, 112.65491), new LatLng(-7.97639, 112.65495),
                        new LatLng(-7.97536, 112.65516), new LatLng(-7.97532, 112.65518),
                        new LatLng(-7.97349, 112.65591), new LatLng(-7.97142, 112.65675), new LatLng(-7.96837, 112.65816),
                        new LatLng(-7.96745, 112.65879), new LatLng(-7.96642, 112.65965), new LatLng(-7.9663, 112.65972),
                        new LatLng(-7.96655, 112.66027), new LatLng(-7.96679, 112.66084), new LatLng(-7.96731, 112.66204),
                        new LatLng(-7.96789, 112.66172), new LatLng(-7.96928, 112.66095), new LatLng(-7.96985, 112.66062),
                        new LatLng(-7.96999, 112.66054), new LatLng(-7.97013, 112.66053),
                        new LatLng(-7.97042, 112.66055), new LatLng(-7.97059, 112.66062), new LatLng(-7.97161, 112.66132),
                        new LatLng(-7.97178, 112.66148), new LatLng(-7.97276, 112.66328),
                        new LatLng(-7.9724, 112.66352), new LatLng(-7.97289, 112.66439), new LatLng(-7.97371, 112.66589),
                        new LatLng(-7.97394, 112.66629), new LatLng(-7.9727, 112.66692),
                        new LatLng(-7.97254, 112.66702), new LatLng(-7.97227, 112.66714), new LatLng(-7.97216, 112.66723),
                        new LatLng(-7.97217, 112.66731), new LatLng(-7.97221, 112.66734), new LatLng(-7.97241, 112.66753), new LatLng(-7.97272, 112.6678),
                        new LatLng(-7.97277, 112.66789), new LatLng(-7.97273, 112.66797), new LatLng(-7.97263, 112.66802),
                        new LatLng(-7.97224, 112.66819), new LatLng(-7.97174, 112.66848), new LatLng(-7.97111, 112.66879));
            }

            public void arjosari_stft() {//at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701), new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.97941, 112.62548), new LatLng(-7.97924, 112.62527), new LatLng(-7.97922, 112.62517),
                        new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203), new LatLng(-7.97112, 112.61216), new LatLng(-7.971, 112.6122),
                        new LatLng(-7.96912, 112.61241), new LatLng(-7.96894, 112.61244), new LatLng(-7.96855, 112.61247),
                        new LatLng(-7.96837, 112.61249), new LatLng(-7.96699, 112.61284), new LatLng(-7.96683, 112.61228),
                        new LatLng(-7.96668, 112.61172), new LatLng(-7.96662, 112.61141), new LatLng(-7.96635, 112.61048),
                        new LatLng(-7.96624, 112.6098), new LatLng(-7.96587, 112.60783), new LatLng(-7.96577, 112.60735));
            }

            public void landungsari_stft() {//gl-at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281), new LatLng(-7.97637, 112.62934), new LatLng(-7.97637, 112.62937),
                        new LatLng(-7.97647, 112.62937), new LatLng(-7.97647, 112.629), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762),new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701), new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.97941, 112.62548), new LatLng(-7.97924, 112.62527), new LatLng(-7.97922, 112.62517),
                        new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203), new LatLng(-7.97112, 112.61216), new LatLng(-7.971, 112.6122),
                        new LatLng(-7.96912, 112.61241), new LatLng(-7.96894, 112.61244), new LatLng(-7.96855, 112.61247),
                        new LatLng(-7.96837, 112.61249), new LatLng(-7.96699, 112.61284), new LatLng(-7.96683, 112.61228),
                        new LatLng(-7.96668, 112.61172), new LatLng(-7.96662, 112.61141), new LatLng(-7.96635, 112.61048),
                        new LatLng(-7.96624, 112.6098), new LatLng(-7.96587, 112.60783), new LatLng(-7.96577, 112.60735));
            }

            public void gadang_stft() {//ldg-at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517),new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203), new LatLng(-7.97112, 112.61216), new LatLng(-7.971, 112.6122),
                        new LatLng(-7.96912, 112.61241), new LatLng(-7.96894, 112.61244), new LatLng(-7.96855, 112.61247),
                        new LatLng(-7.96837, 112.61249), new LatLng(-7.96699, 112.61284), new LatLng(-7.96683, 112.61228),
                        new LatLng(-7.96668, 112.61172), new LatLng(-7.96662, 112.61141), new LatLng(-7.96635, 112.61048),
                        new LatLng(-7.96624, 112.6098), new LatLng(-7.96587, 112.60783), new LatLng(-7.96577, 112.60735));
            }

            public void gadang_stia() {//lg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338),
                        new LatLng(-7.97044, 112.6134),new LatLng(-7.965, 112.61357),new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96175, 112.61339),new LatLng(-7.96094, 112.61364),new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.95777, 112.61353),new LatLng(-7.95755, 112.61347),new LatLng(-7.95679, 112.61286),new LatLng(-7.95665, 112.61275),new LatLng(-7.9543, 112.6106),
                        new LatLng(-7.95358, 112.61029),new LatLng(-7.95291, 112.6098),new LatLng(-7.95227, 112.60928),
                        new LatLng(-7.95208, 112.60919),new LatLng(-7.95147, 112.60901),new LatLng(-7.95141, 112.60899),
                        new LatLng(-7.95092, 112.60891),new LatLng(-7.95068, 112.60889),new LatLng(-7.94815, 112.60876),
                        new LatLng(-7.94797, 112.60875),new LatLng(-7.94738, 112.60881),new LatLng(-7.9471, 112.60883),
                        new LatLng(-7.94698, 112.60887),new LatLng(-7.94681, 112.60893),new LatLng(-7.9434, 112.61015),
                        new LatLng(-7.9432, 112.61025),new LatLng(-7.94313, 112.61032),new LatLng(-7.93963, 112.60786),new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93598, 112.60533),new LatLng(-7.93316, 112.60338),new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93176, 112.6026),new LatLng(-7.93145, 112.60262));
            }


            public void arjosari_stia() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984),
                        new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95461, 112.62051), new LatLng(-7.95291, 112.61903),
                        new LatLng(-7.94996, 112.61545), new LatLng(-7.94985, 112.61536), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94616, 112.61238), new LatLng(-7.94451, 112.61131),
                        new LatLng(-7.94214, 112.60957), new LatLng(-7.93964, 112.60786), new LatLng(-7.93729, 112.60623),
                        new LatLng(-7.93598, 112.60533),new LatLng(-7.93316, 112.60338),new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93176, 112.6026),new LatLng(-7.93145, 112.60262));
            }

            public void landungsari_stia() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262));

            }

            public void arjosari_stie() {//at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701), new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.97941, 112.62548), new LatLng(-7.97924, 112.62527), new LatLng(-7.97922, 112.62517),
                        new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203));
            }

            public void landungsari_stie() {//gl-at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281), new LatLng(-7.97637, 112.62934), new LatLng(-7.97637, 112.62937),
                        new LatLng(-7.97647, 112.62937), new LatLng(-7.97647, 112.629), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762),new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701), new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.97941, 112.62548), new LatLng(-7.97924, 112.62527), new LatLng(-7.97922, 112.62517),
                        new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203));
            }

            public void gadang_stie() {//ldg-at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517),new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203));
            }

            public void arjosari_stie_indocakti() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),new LatLng(-7.96789, 112.63313),
                        new LatLng(-7.97344, 112.63045), new LatLng(-7.97443, 112.62987), new LatLng(-7.97555, 112.62951),new LatLng(-7.97648, 112.62946),
                        new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95956, 112.62512));
            }

            public void gadang_stie_indocakti() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338), new LatLng(-7.97044, 112.6134),new LatLng(-7.965, 112.61357),

                        new LatLng(-7.96393, 112.61352), new LatLng(-7.96391, 112.61405), new LatLng(-7.96398, 112.61439),
                        new LatLng(-7.96427, 112.6151), new LatLng(-7.96443, 112.61531), new LatLng(-7.96449, 112.61542),
                        new LatLng(-7.9646, 112.61572), new LatLng(-7.96467, 112.61587), new LatLng(-7.96635, 112.61868),
                        new LatLng(-7.96671, 112.61938), new LatLng(-7.96442, 112.62066), new LatLng(-7.96429, 112.62077),
                        new LatLng(-7.96411, 112.62095), new LatLng(-7.96382, 112.62131), new LatLng(-7.96343, 112.62195),
                        new LatLng(-7.96328, 112.62228), new LatLng(-7.96305, 112.62331), new LatLng(-7.96302, 112.62361),
                        new LatLng(-7.96304, 112.6238), new LatLng(-7.96308, 112.62413), new LatLng(-7.96335, 112.62547),
                        new LatLng(-7.96266, 112.62573), new LatLng(-7.9623, 112.62584),
                        new LatLng(-7.96225, 112.62581), new LatLng(-7.96217, 112.62518), new LatLng(-7.96212, 112.62492),
                        new LatLng(-7.96209, 112.62474), new LatLng(-7.96211, 112.62396), new LatLng(-7.96189, 112.62353),
                        new LatLng(-7.96173, 112.62347), new LatLng(-7.9616, 112.62347), new LatLng(-7.96149, 112.62341),
                        new LatLng(-7.9614, 112.62333));
            }

            public void landungsari_stie_indocakti() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543));
            }

            public void arjosari_stie_asia() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211), new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704));
            }

            public void gadang_stie_asia() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213));
            }

            public void landungsari_stie_asia() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603),new LatLng(-7.97526, 112.62614),new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624),new LatLng(-7.97548, 112.62623),new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653),new LatLng(-7.97596, 112.62686),new LatLng(-7.97593, 112.62694),new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701),new LatLng(-7.97623, 112.62747),new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773),new LatLng(-7.97642, 112.62788),new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987),new LatLng(-7.97647, 112.63007),new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156),new LatLng(-7.97698, 112.63346),new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348),new LatLng(-7.97758, 112.63361),new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431),new LatLng(-7.97755, 112.63452),new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683),new LatLng(-7.97747, 112.63683),new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788),new LatLng(-7.97347, 112.63856),new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833),new LatLng(-7.96759, 112.6383),new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873),new LatLng(-7.96694, 112.63851),new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821),new LatLng(-7.96598, 112.63761),new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549),new LatLng(-7.96452, 112.63496),new LatLng(-7.96227, 112.63615),
                        new LatLng(-7.96074, 112.63684),new LatLng(-7.96041, 112.637),new LatLng(-7.95707, 112.63804),
                        new LatLng(-7.95505, 112.6385),new LatLng(-7.94753, 112.64012),new LatLng(-7.94546, 112.64075),
                        new LatLng(-7.94315, 112.64165),new LatLng(-7.94185, 112.64223));
            }

            public void arjosari_stie_kertanegara() {//ga-ckl-tst
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.95275, 112.63781), new LatLng(-7.95263, 112.63707), new LatLng(-7.95253, 112.63654),
                        new LatLng(-7.95252, 112.63634), new LatLng(-7.95257, 112.63612), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.9523, 112.63529), new LatLng(-7.95206, 112.63477), new LatLng(-7.95196, 112.63461),new LatLng(-7.95158, 112.63387),
                        new LatLng(-7.95057, 112.63166), new LatLng(-7.95025, 112.63167), new LatLng(-7.94949, 112.63172),
                        new LatLng(-7.94919, 112.63177), new LatLng(-7.94824, 112.63191), new LatLng(-7.94768, 112.63193),
                        new LatLng(-7.9471, 112.63202), new LatLng(-7.94664, 112.63207), new LatLng(-7.94634, 112.63207),new LatLng(-7.94566, 112.632),
                        new LatLng(-7.94525, 112.63199), new LatLng(-7.94443, 112.63187));
            }

            public void gadang_stie_kertanegara() {//abg-ckl-tst
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95275, 112.63781), new LatLng(-7.95263, 112.63707), new LatLng(-7.95253, 112.63654),
                        new LatLng(-7.95252, 112.63634), new LatLng(-7.95257, 112.63612), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.9523, 112.63529), new LatLng(-7.95206, 112.63477), new LatLng(-7.95196, 112.63461),new LatLng(-7.95158, 112.63387),
                        new LatLng(-7.95057, 112.63166), new LatLng(-7.95025, 112.63167), new LatLng(-7.94949, 112.63172),
                        new LatLng(-7.94919, 112.63177), new LatLng(-7.94824, 112.63191), new LatLng(-7.94768, 112.63193),
                        new LatLng(-7.9471, 112.63202), new LatLng(-7.94664, 112.63207), new LatLng(-7.94634, 112.63207),new LatLng(-7.94566, 112.632),
                        new LatLng(-7.94525, 112.63199), new LatLng(-7.94443, 112.63187));
            }

            public void landungsari_stie_kertanegara() {//ckl-tst
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),
                        new LatLng(-7.94934, 112.61572), new LatLng(-7.94575, 112.61828), new LatLng(-7.94451, 112.61934),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95085, 112.63166),
                        new LatLng(-7.95057, 112.63166), new LatLng(-7.95025, 112.63167), new LatLng(-7.94949, 112.63172),
                        new LatLng(-7.94919, 112.63177), new LatLng(-7.94824, 112.63191), new LatLng(-7.94768, 112.63193),
                        new LatLng(-7.9471, 112.63202), new LatLng(-7.94664, 112.63207), new LatLng(-7.94634, 112.63207),new LatLng(-7.94566, 112.632),
                        new LatLng(-7.94525, 112.63199), new LatLng(-7.94443, 112.63187));
            }

            public void gadang_stie_jaya_negara() {//ajg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99672, 112.6297), new LatLng(-7.99288, 112.63141), new LatLng(-7.993, 112.63174),
                        new LatLng(-7.99307, 112.63193), new LatLng(-7.99352, 112.6335),
                        new LatLng(-7.99234, 112.63379), new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389),
                        new LatLng(-7.99137, 112.63408), new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442),
                        new LatLng(-7.98928, 112.63488), new LatLng(-7.98847, 112.633), new LatLng(-7.98672, 112.63372),
                        new LatLng(-7.98622, 112.63394), new LatLng(-7.986, 112.6335), new LatLng(-7.98353, 112.63424),
                        new LatLng(-7.98168, 112.63484), new LatLng(-7.98189, 112.63543), new LatLng(-7.98279, 112.63691),
                        new LatLng(-7.98295, 112.63712), new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97343, 112.63668),
                        new LatLng(-7.97341, 112.63681), new LatLng(-7.97342, 112.63714), new LatLng(-7.97342, 112.63724),new LatLng(-7.97347, 112.63819), new LatLng(-7.97348, 112.63855),
                        new LatLng(-7.9731, 112.63854), new LatLng(-7.9722, 112.63851), new LatLng(-7.97183, 112.63851),
                        new LatLng(-7.9704, 112.63845), new LatLng(-7.96923, 112.63839), new LatLng(-7.96863, 112.63833),
                        new LatLng(-7.9676, 112.63829), new LatLng(-7.96741, 112.63827), new LatLng(-7.96725, 112.63832),
                        new LatLng(-7.96713, 112.6383), new LatLng(-7.96701, 112.63839), new LatLng(-7.96696, 112.63855),
                        new LatLng(-7.96694, 112.6386), new LatLng(-7.96688, 112.63868), new LatLng(-7.96476, 112.63977),
                        new LatLng(-7.96145, 112.64175), new LatLng(-7.96099, 112.64195), new LatLng(-7.96068, 112.64122),
                        new LatLng(-7.96051, 112.64129));
            }

            public void arjosari_stie_jaya_negara() {//asd
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.93096, 112.65042), new LatLng(-7.9317, 112.6501), new LatLng(-7.93244, 112.64987),
                        new LatLng(-7.93363, 112.64972), new LatLng(-7.93378, 112.64972), new LatLng(-7.93404, 112.64972),
                        new LatLng(-7.93439, 112.64972), new LatLng(-7.93562, 112.64972), new LatLng(-7.93609, 112.64974),
                        new LatLng(-7.93642, 112.64975), new LatLng(-7.93825, 112.64977), new LatLng(-7.93914, 112.64973),
                        new LatLng(-7.93948, 112.64968), new LatLng(-7.94012, 112.65045), new LatLng(-7.94107, 112.65148),
                        new LatLng(-7.94156, 112.65212), new LatLng(-7.9422, 112.65268), new LatLng(-7.94292, 112.65302),
                        new LatLng(-7.94344, 112.65332), new LatLng(-7.94373, 112.65367), new LatLng(-7.94445, 112.65485),
                        new LatLng(-7.94579, 112.65694), new LatLng(-7.9467, 112.65803), new LatLng(-7.94675, 112.65809),
                        new LatLng(-7.94749, 112.65903), new LatLng(-7.94763, 112.65914), new LatLng(-7.94791, 112.65925),
                        new LatLng(-7.94795, 112.65889), new LatLng(-7.948, 112.6587), new LatLng(-7.94814, 112.65866),
                        new LatLng(-7.94848, 112.65853), new LatLng(-7.94782, 112.65603), new LatLng(-7.94681, 112.65414),
                        new LatLng(-7.94801, 112.65363), new LatLng(-7.94901, 112.65289), new LatLng(-7.95062, 112.65208),
                        new LatLng(-7.95227, 112.65118), new LatLng(-7.95321, 112.65082), new LatLng(-7.95333, 112.65074),
                        new LatLng(-7.95339, 112.65065), new LatLng(-7.95332, 112.65017), new LatLng(-7.95254, 112.64847),
                        new LatLng(-7.95253, 112.64839), new LatLng(-7.95293, 112.64787), new LatLng(-7.95299, 112.6478),
                        new LatLng(-7.95351, 112.64757), new LatLng(-7.95356, 112.64779), new LatLng(-7.954, 112.6488),
                        new LatLng(-7.95453, 112.65003), new LatLng(-7.95481, 112.64988), new LatLng(-7.95531, 112.64966),
                        new LatLng(-7.95678, 112.64917), new LatLng(-7.95969, 112.64848), new LatLng(-7.95848, 112.64606),
                        new LatLng(-7.95839, 112.64588), new LatLng(-7.95806, 112.64543), new LatLng(-7.95784, 112.64513),
                        new LatLng(-7.95741, 112.64462), new LatLng(-7.95704, 112.6441), new LatLng(-7.9584, 112.6435),
                        new LatLng(-7.95986, 112.64263), new LatLng(-7.96018, 112.64242), new LatLng(-7.96099, 112.64195),
                        new LatLng(-7.96068, 112.64122), new LatLng(-7.96051, 112.64129));
            }

            public void landungsari_stie_jaya_negara() {//adl-asd
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281), new LatLng(-7.97637, 112.62934), new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987), new LatLng(-7.97647, 112.63007), new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97698, 112.63346), new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.97758, 112.63361), new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97755, 112.63452), new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.97747, 112.63683), new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97347, 112.63856), new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.96759, 112.6383), new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96694, 112.63851), new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.96598, 112.63761), new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625), new LatLng(-7.96035, 112.63691), new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.95165, 112.63919), new LatLng(-7.94929, 112.63968), new LatLng(-7.94887, 112.63974),
                        new LatLng(-7.94798, 112.63995), new LatLng(-7.94734, 112.64007),
                        new LatLng(-7.94477, 112.64083), new LatLng(-7.94178, 112.64211), new LatLng(-7.94067, 112.64265), new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454), new LatLng(-7.94375, 112.64821), new LatLng(-7.94279, 112.64862), new LatLng(-7.94129, 112.64929), new LatLng(-7.93948, 112.64968),
                        new LatLng(-7.94012, 112.65045), new LatLng(-7.94107, 112.65148),
                        new LatLng(-7.94156, 112.65212), new LatLng(-7.9422, 112.65268), new LatLng(-7.94292, 112.65302),
                        new LatLng(-7.94344, 112.65332), new LatLng(-7.94373, 112.65367), new LatLng(-7.94445, 112.65485),
                        new LatLng(-7.94579, 112.65694), new LatLng(-7.9467, 112.65803), new LatLng(-7.94675, 112.65809),
                        new LatLng(-7.94749, 112.65903), new LatLng(-7.94763, 112.65914), new LatLng(-7.94791, 112.65925),
                        new LatLng(-7.94795, 112.65889), new LatLng(-7.948, 112.6587), new LatLng(-7.94814, 112.65866),
                        new LatLng(-7.94848, 112.65853), new LatLng(-7.94782, 112.65603), new LatLng(-7.94681, 112.65414),
                        new LatLng(-7.94801, 112.65363), new LatLng(-7.94901, 112.65289), new LatLng(-7.95062, 112.65208),
                        new LatLng(-7.95227, 112.65118), new LatLng(-7.95321, 112.65082), new LatLng(-7.95333, 112.65074),
                        new LatLng(-7.95339, 112.65065), new LatLng(-7.95332, 112.65017), new LatLng(-7.95254, 112.64847),
                        new LatLng(-7.95253, 112.64839), new LatLng(-7.95293, 112.64787), new LatLng(-7.95299, 112.6478),
                        new LatLng(-7.95351, 112.64757), new LatLng(-7.95356, 112.64779), new LatLng(-7.954, 112.6488),
                        new LatLng(-7.95453, 112.65003), new LatLng(-7.95481, 112.64988), new LatLng(-7.95531, 112.64966),
                        new LatLng(-7.95678, 112.64917), new LatLng(-7.95969, 112.64848), new LatLng(-7.95848, 112.64606),
                        new LatLng(-7.95839, 112.64588), new LatLng(-7.95806, 112.64543), new LatLng(-7.95784, 112.64513),
                        new LatLng(-7.95741, 112.64462), new LatLng(-7.95704, 112.6441), new LatLng(-7.9584, 112.6435),
                        new LatLng(-7.95986, 112.64263), new LatLng(-7.96018, 112.64242), new LatLng(-7.96099, 112.64195),
                        new LatLng(-7.96068, 112.64122), new LatLng(-7.96051, 112.64129));
            }

            public void arjosari_stie_koperasi_malang() {//al
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833));
            }

            public void landungsari_stie_koperasi_malang() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603),new LatLng(-7.97526, 112.62614),new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624),new LatLng(-7.97548, 112.62623),new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653),new LatLng(-7.97596, 112.62686),new LatLng(-7.97593, 112.62694),new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701),new LatLng(-7.97623, 112.62747),new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773),new LatLng(-7.97642, 112.62788),new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987),new LatLng(-7.97647, 112.63007),new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156),new LatLng(-7.97698, 112.63346),new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348),new LatLng(-7.97758, 112.63361),new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431),new LatLng(-7.97755, 112.63452),new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683),new LatLng(-7.97747, 112.63683),new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788),new LatLng(-7.97347, 112.63856),new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833));
            }

            public void gadang_stie_koperasi_malang() {//ajg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99672, 112.6297), new LatLng(-7.99288, 112.63141), new LatLng(-7.993, 112.63174),
                        new LatLng(-7.99307, 112.63193), new LatLng(-7.99352, 112.6335),
                        new LatLng(-7.99234, 112.63379), new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389),
                        new LatLng(-7.99137, 112.63408), new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442),
                        new LatLng(-7.98928, 112.63488), new LatLng(-7.98847, 112.633), new LatLng(-7.98672, 112.63372),
                        new LatLng(-7.98622, 112.63394), new LatLng(-7.986, 112.6335), new LatLng(-7.98353, 112.63424),
                        new LatLng(-7.98168, 112.63484), new LatLng(-7.98189, 112.63543), new LatLng(-7.98279, 112.63691),
                        new LatLng(-7.98295, 112.63712), new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97343, 112.63668),
                        new LatLng(-7.97341, 112.63681), new LatLng(-7.97342, 112.63714), new LatLng(-7.97342, 112.63724), new LatLng(-7.97347, 112.63819), new LatLng(-7.97348, 112.63855),
                        new LatLng(-7.9731, 112.63854), new LatLng(-7.9722, 112.63851), new LatLng(-7.97183, 112.63851),
                        new LatLng(-7.9704, 112.63845), new LatLng(-7.96923, 112.63839), new LatLng(-7.96863, 112.63833));
            }

            public void arjosari_stip() {//ga-ckl-tst
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.95275, 112.63781), new LatLng(-7.95263, 112.63707), new LatLng(-7.95253, 112.63654),
                        new LatLng(-7.95252, 112.63634), new LatLng(-7.95257, 112.63612), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.9523, 112.63529), new LatLng(-7.95206, 112.63477), new LatLng(-7.95196, 112.63461),new LatLng(-7.95158, 112.63387),
                        new LatLng(-7.95057, 112.63166), new LatLng(-7.95025, 112.63167), new LatLng(-7.94949, 112.63172),
                        new LatLng(-7.94919, 112.63177), new LatLng(-7.94824, 112.63191), new LatLng(-7.94768, 112.63193),
                        new LatLng(-7.9471, 112.63202), new LatLng(-7.94664, 112.63207), new LatLng(-7.94634, 112.63207),new LatLng(-7.94566, 112.632));
            }

            public void gadang_stip() {//abg-ckl-tst
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95275, 112.63781), new LatLng(-7.95263, 112.63707), new LatLng(-7.95253, 112.63654),
                        new LatLng(-7.95252, 112.63634), new LatLng(-7.95257, 112.63612), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.9523, 112.63529), new LatLng(-7.95206, 112.63477), new LatLng(-7.95196, 112.63461),new LatLng(-7.95158, 112.63387),
                        new LatLng(-7.95057, 112.63166), new LatLng(-7.95025, 112.63167), new LatLng(-7.94949, 112.63172),
                        new LatLng(-7.94919, 112.63177), new LatLng(-7.94824, 112.63191), new LatLng(-7.94768, 112.63193),
                        new LatLng(-7.9471, 112.63202), new LatLng(-7.94664, 112.63207), new LatLng(-7.94634, 112.63207),new LatLng(-7.94566, 112.632));
            }

            public void landungsari_stip() {//ckl-tst
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),
                        new LatLng(-7.94934, 112.61572), new LatLng(-7.94575, 112.61828), new LatLng(-7.94451, 112.61934),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95085, 112.63166),
                        new LatLng(-7.95057, 112.63166), new LatLng(-7.95025, 112.63167), new LatLng(-7.94949, 112.63172),
                        new LatLng(-7.94919, 112.63177), new LatLng(-7.94824, 112.63191), new LatLng(-7.94768, 112.63193),
                        new LatLng(-7.9471, 112.63202), new LatLng(-7.94664, 112.63207), new LatLng(-7.94634, 112.63207),new LatLng(-7.94566, 112.632));
            }

            public void arjosari_stt_internasional() {//asd
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.93096, 112.65042), new LatLng(-7.9317, 112.6501), new LatLng(-7.93244, 112.64987),
                        new LatLng(-7.93363, 112.64972), new LatLng(-7.93378, 112.64972), new LatLng(-7.93404, 112.64972),
                        new LatLng(-7.93439, 112.64972), new LatLng(-7.93562, 112.64972), new LatLng(-7.93609, 112.64974),
                        new LatLng(-7.93642, 112.64975), new LatLng(-7.93744, 112.64977));
            }

            public void landungsari_stt_internasional() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603),new LatLng(-7.97526, 112.62614),new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624),new LatLng(-7.97548, 112.62623),new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653),new LatLng(-7.97596, 112.62686),new LatLng(-7.97593, 112.62694),new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701),new LatLng(-7.97623, 112.62747),new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773),new LatLng(-7.97642, 112.62788),new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987),new LatLng(-7.97647, 112.63007),new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156),new LatLng(-7.97698, 112.63346),new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348),new LatLng(-7.97758, 112.63361),new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431),new LatLng(-7.97755, 112.63452),new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683),new LatLng(-7.97747, 112.63683),new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788),new LatLng(-7.97347, 112.63856),new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833),new LatLng(-7.96759, 112.6383),new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873),new LatLng(-7.96694, 112.63851),new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821),new LatLng(-7.96598, 112.63761),new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549),new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96226, 112.63602),new LatLng(-7.96185, 112.63625),new LatLng(-7.96035, 112.63691),new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.95165, 112.63919),new LatLng(-7.94929, 112.63968),new LatLng(-7.94887, 112.63974),
                        new LatLng(-7.94798, 112.63995),new LatLng(-7.94734, 112.64007),
                        new LatLng(-7.94477, 112.64083),new LatLng(-7.94178, 112.64211),new LatLng(-7.94067, 112.64265),new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454),new LatLng(-7.94375, 112.64821),new LatLng(-7.94279, 112.64862),
                        new LatLng(-7.94129, 112.64929),new LatLng(-7.93914, 112.64973),
                        new LatLng(-7.93744, 112.64977));
            }

            public void gadang_stt_internasional() {//amg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.94067, 112.64265),new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454),new LatLng(-7.94375, 112.64821),new LatLng(-7.94279, 112.64862),
                        new LatLng(-7.94129, 112.64929),new LatLng(-7.93914, 112.64973),
                        new LatLng(-7.93744, 112.64977));
            }

            public void arjosari_stiki() {//at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701), new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.97941, 112.62548), new LatLng(-7.97924, 112.62527), new LatLng(-7.97922, 112.62517),
                        new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203), new LatLng(-7.97112, 112.61216), new LatLng(-7.971, 112.6122),
                        new LatLng(-7.96912, 112.61241), new LatLng(-7.96894, 112.61244), new LatLng(-7.96855, 112.61247),
                        new LatLng(-7.96837, 112.61249), new LatLng(-7.96699, 112.61284), new LatLng(-7.96683, 112.61228),
                        new LatLng(-7.96668, 112.61172), new LatLng(-7.96662, 112.61141), new LatLng(-7.96635, 112.61048),
                        new LatLng(-7.96624, 112.6098), new LatLng(-7.96587, 112.60783), new LatLng(-7.96577, 112.60735));
            }

            public void landungsari_stiki() {//gl-at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281), new LatLng(-7.97637, 112.62934), new LatLng(-7.97637, 112.62937),
                        new LatLng(-7.97647, 112.62937), new LatLng(-7.97647, 112.629), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762),new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701), new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.97941, 112.62548), new LatLng(-7.97924, 112.62527), new LatLng(-7.97922, 112.62517),
                        new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203), new LatLng(-7.97112, 112.61216), new LatLng(-7.971, 112.6122),
                        new LatLng(-7.96912, 112.61241), new LatLng(-7.96894, 112.61244), new LatLng(-7.96855, 112.61247),
                        new LatLng(-7.96837, 112.61249), new LatLng(-7.96699, 112.61284), new LatLng(-7.96683, 112.61228),
                        new LatLng(-7.96668, 112.61172), new LatLng(-7.96662, 112.61141), new LatLng(-7.96635, 112.61048),
                        new LatLng(-7.96624, 112.6098), new LatLng(-7.96587, 112.60783), new LatLng(-7.96577, 112.60735));
            }

            public void gadang_stiki() {//ldg-at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517),new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203), new LatLng(-7.97112, 112.61216), new LatLng(-7.971, 112.6122),
                        new LatLng(-7.96912, 112.61241), new LatLng(-7.96894, 112.61244), new LatLng(-7.96855, 112.61247),
                        new LatLng(-7.96837, 112.61249), new LatLng(-7.96699, 112.61284), new LatLng(-7.96683, 112.61228),
                        new LatLng(-7.96668, 112.61172), new LatLng(-7.96662, 112.61141), new LatLng(-7.96635, 112.61048),
                        new LatLng(-7.96624, 112.6098), new LatLng(-7.96587, 112.60783), new LatLng(-7.96577, 112.60735));
            }

            public void landungsari_stikes_maharani() {//ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94214, 112.60957),
                        new LatLng(-7.94279, 112.61008), new LatLng(-7.94156, 112.61125), new LatLng(-7.94114, 112.61085),
                        new LatLng(-7.94043, 112.61145), new LatLng(-7.9402, 112.61159), new LatLng(-7.93978, 112.61178),
                        new LatLng(-7.93926, 112.61185), new LatLng(-7.93861, 112.61188),
                        new LatLng(-7.93812, 112.61182), new LatLng(-7.93787, 112.61176), new LatLng(-7.93745, 112.61152),
                        new LatLng(-7.9373, 112.61152), new LatLng(-7.93706, 112.61163), new LatLng(-7.93664, 112.61186),
                        new LatLng(-7.93625, 112.61206), new LatLng(-7.93555, 112.61242), new LatLng(-7.93532, 112.6126),
                        new LatLng(-7.93494, 112.61313), new LatLng(-7.93482, 112.61315), new LatLng(-7.9345, 112.61316));
            }

            public void arjosari_stikes_maharani() {//abg-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211), new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606),
                        new LatLng(-7.93915, 112.63577),new LatLng(-7.93919, 112.63531), new LatLng(-7.93916, 112.6351), new LatLng(-7.93888, 112.63425),
                        new LatLng(-7.93883, 112.63398), new LatLng(-7.93884, 112.63374), new LatLng(-7.93896, 112.6334),new LatLng(-7.93904, 112.63332),
                        new LatLng(-7.93928, 112.63302), new LatLng(-7.93934, 112.63288), new LatLng(-7.93937, 112.6327),
                        new LatLng(-7.93946, 112.63214), new LatLng(-7.93943, 112.63165), new LatLng(-7.93937, 112.63144),
                        new LatLng(-7.93923, 112.63105),new LatLng(-7.93763, 112.62768), new LatLng(-7.93715, 112.62687), new LatLng(-7.93701, 112.62648),
                        new LatLng(-7.93705, 112.62641), new LatLng(-7.93705, 112.62634), new LatLng(-7.93731, 112.62615),new LatLng(-7.9384, 112.6256),
                        new LatLng(-7.93953, 112.625),new LatLng(-7.93944, 112.62487),new LatLng(-7.93905, 112.62409),
                        new LatLng(-7.93844, 112.62292),new LatLng(-7.93827, 112.62225),new LatLng(-7.93782, 112.62118),
                        new LatLng(-7.93764, 112.62085),new LatLng(-7.9371, 112.62005),new LatLng(-7.93691, 112.61976),
                        new LatLng(-7.9367, 112.61927),new LatLng(-7.93631, 112.61865),new LatLng(-7.93631, 112.61865),
                        new LatLng(-7.93611, 112.61829),new LatLng(-7.93547, 112.61723),
                        new LatLng(-7.93479, 112.61579),new LatLng(-7.93447, 112.61524),new LatLng(-7.93312, 112.61338),
                        new LatLng(-7.93303, 112.61329),new LatLng(-7.93387, 112.61321),new LatLng(-7.9345, 112.61316));
            }

            public void gadang_stikes_maharani() {//abg-ckl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387), new LatLng(-7.96451, 112.63496),
                        new LatLng(-7.96256, 112.63141), new LatLng(-7.96205, 112.63055), new LatLng(-7.96166, 112.63087),
                        new LatLng(-7.96116, 112.63129), new LatLng(-7.96076, 112.63151), new LatLng(-7.96054, 112.63158),
                        new LatLng(-7.95964, 112.63205), new LatLng(-7.95955, 112.63189), new LatLng(-7.95919, 112.63141),
                        new LatLng(-7.95893, 112.63114), new LatLng(-7.95832, 112.63148), new LatLng(-7.95806, 112.6316),
                        new LatLng(-7.95789, 112.63168), new LatLng(-7.95768, 112.63176), new LatLng(-7.95763, 112.63167),
                        new LatLng(-7.95695, 112.63158), new LatLng(-7.95682, 112.63159), new LatLng(-7.95664, 112.63159),
                        new LatLng(-7.95656, 112.63153), new LatLng(-7.95608, 112.63149), new LatLng(-7.95558, 112.6315),
                        new LatLng(-7.95545, 112.63152), new LatLng(-7.9545, 112.63162), new LatLng(-7.95426, 112.63166),
                        new LatLng(-7.95288, 112.63178), new LatLng(-7.95264, 112.63179), new LatLng(-7.95197, 112.6317),
                        new LatLng(-7.95189, 112.63175), new LatLng(-7.95124, 112.63161), new LatLng(-7.95093, 112.63165),
                        new LatLng(-7.95085, 112.63167), new LatLng(-7.95037, 112.63),
                        new LatLng(-7.95005, 112.62944), new LatLng(-7.94902, 112.62736), new LatLng(-7.94628, 112.62227),
                        new LatLng(-7.94619, 112.62203), new LatLng(-7.94594, 112.62162), new LatLng(-7.94573, 112.62132),
                        new LatLng(-7.94527, 112.62061), new LatLng(-7.94497, 112.6201), new LatLng(-7.9446, 112.61948),
                        new LatLng(-7.9445, 112.61935), new LatLng(-7.94153, 112.62272), new LatLng(-7.94037, 112.62404),
                        new LatLng(-7.93965, 112.62488), new LatLng(-7.93953, 112.625),new LatLng(-7.93944, 112.62487),new LatLng(-7.93905, 112.62409),
                        new LatLng(-7.93844, 112.62292),new LatLng(-7.93827, 112.62225),new LatLng(-7.93782, 112.62118),
                        new LatLng(-7.93764, 112.62085),new LatLng(-7.9371, 112.62005),new LatLng(-7.93691, 112.61976),
                        new LatLng(-7.9367, 112.61927),new LatLng(-7.93631, 112.61865),new LatLng(-7.93631, 112.61865),
                        new LatLng(-7.93611, 112.61829),new LatLng(-7.93547, 112.61723),
                        new LatLng(-7.93479, 112.61579),new LatLng(-7.93447, 112.61524),new LatLng(-7.93312, 112.61338),
                        new LatLng(-7.93303, 112.61329),new LatLng(-7.93387, 112.61321),new LatLng(-7.9345, 112.61316));
            }

            public void arjosari_stikes_widyagama() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211), new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606),

                        new LatLng(-7.93915, 112.63577), new LatLng(-7.93919, 112.63531), new LatLng(-7.93916, 112.6351),
                        new LatLng(-7.93888, 112.63425),
                        new LatLng(-7.93883, 112.63398), new LatLng(-7.93884, 112.63374), new LatLng(-7.93896, 112.6334),
                        new LatLng(-7.93904, 112.63332));
            }

            public void gadang_stikes_widyagama() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606),
                        new LatLng(-7.93915, 112.63577), new LatLng(-7.93919, 112.63531), new LatLng(-7.93916, 112.6351),
                        new LatLng(-7.93888, 112.63425),
                        new LatLng(-7.93883, 112.63398), new LatLng(-7.93884, 112.63374), new LatLng(-7.93896, 112.6334),
                        new LatLng(-7.93904, 112.63332));
            }

            public void landungsari_stikes_widyagama() {//ckl-abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),
                        new LatLng(-7.94934, 112.61572), new LatLng(-7.94575, 112.61828), new LatLng(-7.94451, 112.61934),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95122, 112.63285),
                        new LatLng(-7.95158, 112.63387), new LatLng(-7.95196, 112.63461),
                        new LatLng(-7.95206, 112.63477), new LatLng(-7.9523, 112.63529), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.95257, 112.63612), new LatLng(-7.95252, 112.63634),
                        new LatLng(-7.95253, 112.63654), new LatLng(-7.95263, 112.63707), new LatLng(-7.95275, 112.63781),
                        new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.94753, 112.64012),new LatLng(-7.94546, 112.64075),
                        new LatLng(-7.94315, 112.64165),new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211),new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606),
                        new LatLng(-7.93915, 112.63577), new LatLng(-7.93919, 112.63531), new LatLng(-7.93916, 112.6351),
                        new LatLng(-7.93888, 112.63425),
                        new LatLng(-7.93883, 112.63398), new LatLng(-7.93884, 112.63374), new LatLng(-7.93896, 112.6334),
                        new LatLng(-7.93904, 112.63332));
            }

            public void arjosari_stikes_kendedes() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.93096, 112.65042), new LatLng(-7.9317, 112.6501),new LatLng(-7.93244, 112.64987), new LatLng(-7.93363, 112.64972), new LatLng(-7.93378, 112.64972),
                        new LatLng(-7.93404, 112.64972), new LatLng(-7.93378, 112.64972),new LatLng(-7.93363, 112.64972), new LatLng(-7.93244, 112.64987), new LatLng(-7.93199, 112.65001));
            }

            public void landungsari_stikes_kendedes() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603),new LatLng(-7.97526, 112.62614),new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624),new LatLng(-7.97548, 112.62623),new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653),new LatLng(-7.97596, 112.62686),new LatLng(-7.97593, 112.62694),new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701),new LatLng(-7.97623, 112.62747),new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773),new LatLng(-7.97642, 112.62788),new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987),new LatLng(-7.97647, 112.63007),new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156),new LatLng(-7.97698, 112.63346),new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348),new LatLng(-7.97758, 112.63361),new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431),new LatLng(-7.97755, 112.63452),new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683),new LatLng(-7.97747, 112.63683),new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788),new LatLng(-7.97347, 112.63856),new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833),new LatLng(-7.96759, 112.6383),new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873),new LatLng(-7.96694, 112.63851),new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821),new LatLng(-7.96598, 112.63761),new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549),new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96226, 112.63602),new LatLng(-7.96185, 112.63625),new LatLng(-7.96035, 112.63691),new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.95165, 112.63919),new LatLng(-7.94929, 112.63968),new LatLng(-7.94887, 112.63974),
                        new LatLng(-7.94798, 112.63995),new LatLng(-7.94734, 112.64007),
                        new LatLng(-7.94477, 112.64083),new LatLng(-7.94178, 112.64211),new LatLng(-7.94067, 112.64265),new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454),new LatLng(-7.94375, 112.64821),new LatLng(-7.94279, 112.64862),
                        new LatLng(-7.94129, 112.64929),new LatLng(-7.93914, 112.64973),
                        new LatLng(-7.93642, 112.64975),new LatLng(-7.93609, 112.64974),new LatLng(-7.93562, 112.64972),new LatLng(-7.93439, 112.64972),
                        new LatLng(-7.93404, 112.64972), new LatLng(-7.93378, 112.64972),new LatLng(-7.93363, 112.64972), new LatLng(-7.93244, 112.64987), new LatLng(-7.93199, 112.65001));
            }

            public void gadang_stikes_kendedes() {//amg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.94067, 112.64265),new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454),new LatLng(-7.94375, 112.64821),new LatLng(-7.94279, 112.64862),
                        new LatLng(-7.94129, 112.64929),new LatLng(-7.93914, 112.64973),
                        new LatLng(-7.93642, 112.64975),new LatLng(-7.93609, 112.64974),new LatLng(-7.93562, 112.64972),new LatLng(-7.93439, 112.64972),
                        new LatLng(-7.93404, 112.64972), new LatLng(-7.93378, 112.64972),new LatLng(-7.93363, 112.64972), new LatLng(-7.93244, 112.64987), new LatLng(-7.93199, 112.65001));
            }

            public void arjosari_stie_malangkucecwara() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211), new LatLng(-7.93998, 112.63772));
            }

            public void gadang_stie_malangkucecwara() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.93998, 112.63772));
            }

            public void landungsari_stie_malangkucecwara() {//ckl-abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),
                        new LatLng(-7.94934, 112.61572), new LatLng(-7.94575, 112.61828), new LatLng(-7.94451, 112.61934),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95122, 112.63285),
                        new LatLng(-7.95158, 112.63387), new LatLng(-7.95196, 112.63461),
                        new LatLng(-7.95206, 112.63477), new LatLng(-7.9523, 112.63529), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.95257, 112.63612), new LatLng(-7.95252, 112.63634),
                        new LatLng(-7.95253, 112.63654), new LatLng(-7.95263, 112.63707), new LatLng(-7.95275, 112.63781),
                        new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.94753, 112.64012),new LatLng(-7.94546, 112.64075),
                        new LatLng(-7.94315, 112.64165),new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211),new LatLng(-7.93998, 112.63772));
            }

            public void landungsari_stmik_paramitha() {//adl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),new LatLng(-7.94996, 112.61545),new LatLng(-7.95291, 112.61903),new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228),new LatLng(-7.95784, 112.62342),new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577),new LatLng(-7.96139, 112.62593),new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594),new LatLng(-7.96214, 112.62584),new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584),new LatLng(-7.96266, 112.62573),new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535),new LatLng(-7.96448, 112.62503),new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487),new LatLng(-7.96569, 112.62486),new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375),new LatLng(-7.96846, 112.62379),new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194),new LatLng(-7.97213, 112.62186),new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202),new LatLng(-7.97245, 112.62202),new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349),new LatLng(-7.9734, 112.62405),new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603),new LatLng(-7.97526, 112.62614),new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624),new LatLng(-7.97548, 112.62623),new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653),new LatLng(-7.97596, 112.62686),new LatLng(-7.97593, 112.62694),new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701),new LatLng(-7.97623, 112.62747),new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773),new LatLng(-7.97642, 112.62788),new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62949),
                        new LatLng(-7.97642, 112.62987),new LatLng(-7.97647, 112.63007),new LatLng(-7.97651, 112.63015),
                        new LatLng(-7.97656, 112.63156),new LatLng(-7.97698, 112.63346),new LatLng(-7.9771, 112.63344),
                        new LatLng(-7.97735, 112.63348),new LatLng(-7.97758, 112.63361),new LatLng(-7.97773, 112.63397),
                        new LatLng(-7.97768, 112.63431),new LatLng(-7.97755, 112.63452),new LatLng(-7.9773, 112.63464),
                        new LatLng(-7.97767, 112.63683),new LatLng(-7.97747, 112.63683),new LatLng(-7.97338, 112.63667),
                        new LatLng(-7.97346, 112.63788),new LatLng(-7.97347, 112.63856),new LatLng(-7.97038, 112.63844),
                        new LatLng(-7.96855, 112.63833),new LatLng(-7.96759, 112.6383),new LatLng(-7.96736, 112.63862),
                        new LatLng(-7.96708, 112.63873),new LatLng(-7.96694, 112.63851),new LatLng(-7.9667, 112.63823),
                        new LatLng(-7.96633, 112.63821),new LatLng(-7.96598, 112.63761),new LatLng(-7.96552, 112.6368),
                        new LatLng(-7.96478, 112.63549),new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96226, 112.63602),new LatLng(-7.96185, 112.63625),new LatLng(-7.96035, 112.63691),new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.95165, 112.63919),new LatLng(-7.94929, 112.63968),new LatLng(-7.94887, 112.63974),
                        new LatLng(-7.94798, 112.63995),new LatLng(-7.94734, 112.64007),
                        new LatLng(-7.94477, 112.64083),new LatLng(-7.94178, 112.64211),new LatLng(-7.94067, 112.64265),new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454),new LatLng(-7.94375, 112.64821));
            }

            public void gadang_stmik_paramitha() {//amg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.94067, 112.64265),new LatLng(-7.94174, 112.64461),
                        new LatLng(-7.94224, 112.6454),new LatLng(-7.94375, 112.64821));
            }

            public void arjosari_stmik_paramitha() {//asd
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.93096, 112.65042), new LatLng(-7.9317, 112.6501), new LatLng(-7.93244, 112.64987),
                        new LatLng(-7.93363, 112.64972), new LatLng(-7.93378, 112.64972), new LatLng(-7.93404, 112.64972),
                        new LatLng(-7.93439, 112.64972), new LatLng(-7.93562, 112.64972), new LatLng(-7.93609, 112.64974),
                        new LatLng(-7.93642, 112.64975), new LatLng(-7.93744, 112.64977),

                        new LatLng(-7.93825, 112.64977), new LatLng(-7.93914, 112.64973), new LatLng(-7.93948, 112.64967),
                        new LatLng(-7.94129, 112.64929), new LatLng(-7.94279, 112.64862), new LatLng(-7.94375, 112.64821));
            }

            public void arjosari_stt_malang() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211), new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606),
                        new LatLng(-7.93915, 112.63577),new LatLng(-7.93919, 112.63531), new LatLng(-7.93916, 112.6351), new LatLng(-7.93888, 112.63425),
                        new LatLng(-7.93883, 112.63398), new LatLng(-7.93884, 112.63374), new LatLng(-7.93896, 112.6334),new LatLng(-7.93904, 112.63332),
                        new LatLng(-7.93928, 112.63302), new LatLng(-7.93934, 112.63288), new LatLng(-7.93937, 112.6327),
                        new LatLng(-7.93946, 112.63214), new LatLng(-7.93943, 112.63165), new LatLng(-7.93937, 112.63144),
                        new LatLng(-7.93923, 112.63105),new LatLng(-7.9383, 112.62912));
            }

            public void gadang_stt_malang() {//abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95183, 112.63914), new LatLng(-7.94927, 112.63968), new LatLng(-7.94798, 112.63995),
                        new LatLng(-7.9473, 112.64008), new LatLng(-7.94474, 112.64084), new LatLng(-7.9418, 112.64213),
                        new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606),
                        new LatLng(-7.93915, 112.63577), new LatLng(-7.93919, 112.63531), new LatLng(-7.93916, 112.6351),
                        new LatLng(-7.93888, 112.63425),
                        new LatLng(-7.93883, 112.63398), new LatLng(-7.93884, 112.63374), new LatLng(-7.93896, 112.6334),
                        new LatLng(-7.93904, 112.63332), new LatLng(-7.93928, 112.63302), new LatLng(-7.93934, 112.63288), new LatLng(-7.93937, 112.6327),
                        new LatLng(-7.93946, 112.63214), new LatLng(-7.93943, 112.63165), new LatLng(-7.93937, 112.63144),
                        new LatLng(-7.93923, 112.63105),new LatLng(-7.9383, 112.62912));
            }

            public void landungsari_stt_malang() {//ckl-abg
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),
                        new LatLng(-7.94934, 112.61572), new LatLng(-7.94575, 112.61828), new LatLng(-7.94451, 112.61934),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95122, 112.63285),
                        new LatLng(-7.95158, 112.63387), new LatLng(-7.95196, 112.63461),
                        new LatLng(-7.95206, 112.63477), new LatLng(-7.9523, 112.63529), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.95257, 112.63612), new LatLng(-7.95252, 112.63634),
                        new LatLng(-7.95253, 112.63654), new LatLng(-7.95263, 112.63707), new LatLng(-7.95275, 112.63781),
                        new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.94753, 112.64012),new LatLng(-7.94546, 112.64075),
                        new LatLng(-7.94315, 112.64165),new LatLng(-7.94185, 112.64223),
                        new LatLng(-7.94178, 112.64211),new LatLng(-7.93998, 112.63772), new LatLng(-7.93967, 112.63704),
                        new LatLng(-7.93959, 112.63688), new LatLng(-7.93953, 112.63681), new LatLng(-7.9392, 112.63606),
                        new LatLng(-7.93915, 112.63577), new LatLng(-7.93919, 112.63531), new LatLng(-7.93916, 112.6351),
                        new LatLng(-7.93888, 112.63425),
                        new LatLng(-7.93883, 112.63398), new LatLng(-7.93884, 112.63374), new LatLng(-7.93896, 112.6334),
                        new LatLng(-7.93904, 112.63332), new LatLng(-7.93928, 112.63302), new LatLng(-7.93934, 112.63288), new LatLng(-7.93937, 112.6327),
                        new LatLng(-7.93946, 112.63214), new LatLng(-7.93943, 112.63165), new LatLng(-7.93937, 112.63144),
                        new LatLng(-7.93923, 112.63105),new LatLng(-7.9383, 112.62912));
            }

            public void arjosari_stai_ma_had_aly_al_hikam() {//ga-ckl-tst
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),new LatLng(-7.95297, 112.63884),
                        new LatLng(-7.95275, 112.63781), new LatLng(-7.95263, 112.63707), new LatLng(-7.95253, 112.63654),
                        new LatLng(-7.95252, 112.63634), new LatLng(-7.95257, 112.63612), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.9523, 112.63529), new LatLng(-7.95206, 112.63477), new LatLng(-7.95196, 112.63461),new LatLng(-7.95158, 112.63387),
                        new LatLng(-7.95057, 112.63166), new LatLng(-7.95025, 112.63167), new LatLng(-7.94949, 112.63172),
                        new LatLng(-7.94919, 112.63177), new LatLng(-7.94907, 112.63179));
            }

            public void gadang_stai_ma_had_aly_al_hikam() {//abg-ckl-tst
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),
                        new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338), new LatLng(-7.99234, 112.63379),
                        new LatLng(-7.99227, 112.63384), new LatLng(-7.992, 112.63389), new LatLng(-7.99137, 112.63408),
                        new LatLng(-7.9906, 112.63434), new LatLng(-7.99052, 112.63442), new LatLng(-7.9871, 112.63571),
                        new LatLng(-7.98286, 112.63714), new LatLng(-7.98105, 112.63788), new LatLng(-7.9809, 112.6379),
                        new LatLng(-7.98032, 112.63727), new LatLng(-7.97973, 112.63705), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97415, 112.63672), new LatLng(-7.97339, 112.63668), new LatLng(-7.9724, 112.63658),
                        new LatLng(-7.96931, 112.63634), new LatLng(-7.96916, 112.63635), new LatLng(-7.96911, 112.63625),
                        new LatLng(-7.96782, 112.63461), new LatLng(-7.96713, 112.63351), new LatLng(-7.96709, 112.63344),
                        new LatLng(-7.96636, 112.63387),new LatLng(-7.96451, 112.63496), new LatLng(-7.96226, 112.63602), new LatLng(-7.96185, 112.63625),
                        new LatLng(-7.96036, 112.6369),new LatLng(-7.95881, 112.63745), new LatLng(-7.95706, 112.63792), new LatLng(-7.95296, 112.63885),
                        new LatLng(-7.95275, 112.63781), new LatLng(-7.95263, 112.63707), new LatLng(-7.95253, 112.63654),
                        new LatLng(-7.95252, 112.63634), new LatLng(-7.95257, 112.63612), new LatLng(-7.95254, 112.63597),
                        new LatLng(-7.9523, 112.63529), new LatLng(-7.95206, 112.63477), new LatLng(-7.95196, 112.63461),new LatLng(-7.95158, 112.63387),
                        new LatLng(-7.95057, 112.63166), new LatLng(-7.95025, 112.63167), new LatLng(-7.94949, 112.63172),
                        new LatLng(-7.94919, 112.63177), new LatLng(-7.94907, 112.63179));
            }

            public void landungsari_stai_ma_had_aly_al_hikam() {//ckl-tst
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032),
                        new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536),
                        new LatLng(-7.94934, 112.61572), new LatLng(-7.94575, 112.61828), new LatLng(-7.94451, 112.61934),
                        new LatLng(-7.94461, 112.61948), new LatLng(-7.94527, 112.6206), new LatLng(-7.94593, 112.6216),
                        new LatLng(-7.94599, 112.62171), new LatLng(-7.94619, 112.62203), new LatLng(-7.94628, 112.62228),
                        new LatLng(-7.94752, 112.62464), new LatLng(-7.94807, 112.62555), new LatLng(-7.94901, 112.62735),
                        new LatLng(-7.94915, 112.62767), new LatLng(-7.95006, 112.62946),
                        new LatLng(-7.95031, 112.62986), new LatLng(-7.95047, 112.63031), new LatLng(-7.95085, 112.63166),
                        new LatLng(-7.95057, 112.63166), new LatLng(-7.95025, 112.63167), new LatLng(-7.94949, 112.63172),
                        new LatLng(-7.94919, 112.63177), new LatLng(-7.94907, 112.63179));
            }

            public void arjosari_upbjj(){//ga
                mRouteExample=GoogleMapHelper.createMapRoute(new LatLng(-7.93299,112.65769),
                        new LatLng(-7.93285,112.65687),new LatLng(-7.93212,112.65512),new LatLng(-7.93027,112.65068),
                        new LatLng(-7.92937,112.6489),new LatLng(-7.93077,112.6481),new LatLng(-7.93581,112.64524),
                        new LatLng(-7.94315,112.64165),new LatLng(-7.94546,112.64075),new LatLng(-7.94753,112.64012),
                        new LatLng(-7.95505,112.6385),new LatLng(-7.95707,112.63804),new LatLng(-7.96041,112.637),
                        new LatLng(-7.96074,112.63684),new LatLng(-7.96227,112.63615),new LatLng(-7.96452,112.63496),
                        new LatLng(-7.96478,112.63549),new LatLng(-7.96552,112.6368),new LatLng(-7.96598,112.63761),
                        new LatLng(-7.96633,112.63821),new LatLng(-7.9667,112.63823),new LatLng(-7.96694,112.63851),
                        new LatLng(-7.96708,112.63873),new LatLng(-7.96736,112.63862),new LatLng(-7.96759,112.6383),
                        new LatLng(-7.96855,112.63833),new LatLng(-7.97038,112.63844),new LatLng(-7.97347,112.63856),
                        new LatLng(-7.97346,112.63788),new LatLng(-7.97338,112.63667),new LatLng(-7.97747,112.63683),
                        new LatLng(-7.97767,112.63683),new LatLng(-7.9773,112.63464),new LatLng(-7.97755,112.63452),
                        new LatLng(-7.97768,112.63431),new LatLng(-7.97773,112.63397),new LatLng(-7.97758,112.63361),
                        new LatLng(-7.97874,112.63257),new LatLng(-7.9788,112.6325),new LatLng(-7.97912,112.63142),
                        new LatLng(-7.97936,112.63118),new LatLng(-7.98025,112.63089),new LatLng(-7.98046,112.63066),
                        new LatLng(-7.98066,112.6306),new LatLng(-7.98159,112.63041),new LatLng(-7.98187,112.63168),
                        new LatLng(-7.9836,112.63133),new LatLng(-7.98522,112.63093),new LatLng(-7.98739,112.63043),
                        new LatLng(-7.98735,112.63031),new LatLng(-7.98822,112.62995),new LatLng(-7.98859,112.62968),
                        new LatLng(-7.9886,112.62962),new LatLng(-7.98831,112.62817),new LatLng(-7.98785,112.62588),
                        new LatLng(-7.98751,112.62508),new LatLng(-7.98654,112.62354),new LatLng(-7.98812,112.62223),
                        new LatLng(-7.98876,112.62204),new LatLng(-7.99094,112.62141),new LatLng(-8.00117,112.61788),
                        new LatLng(-8.00284,112.61773),new LatLng(-8.00428,112.61799),new LatLng(-8.00703,112.61884),

                        new LatLng(-8.01042, 112.62003),new LatLng(-8.0123, 112.6204),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01567, 112.62022),new LatLng(-8.01818, 112.61981),
                        new LatLng(-8.02015, 112.61906),new LatLng(-8.02036, 112.61955),new LatLng(-8.02194, 112.62391),new LatLng(-8.02248, 112.62621),new LatLng(-8.0228, 112.6276));
            }

            public void landungsari_upbjj() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.9432, 112.61025),
                        new LatLng(-7.9434, 112.61015), new LatLng(-7.94681, 112.60893), new LatLng(-7.94698, 112.60887),
                        new LatLng(-7.9471, 112.60883), new LatLng(-7.94738, 112.60881), new LatLng(-7.94797, 112.60875),
                        new LatLng(-7.94815, 112.60876), new LatLng(-7.95068, 112.60889), new LatLng(-7.95092, 112.60891),
                        new LatLng(-7.95141, 112.60899), new LatLng(-7.95147, 112.60901), new LatLng(-7.95208, 112.60919),
                        new LatLng(-7.95227, 112.60928), new LatLng(-7.95291, 112.6098), new LatLng(-7.95358, 112.61029),
                        new LatLng(-7.9543, 112.6106), new LatLng(-7.95665, 112.61275), new LatLng(-7.95679, 112.61286),
                        new LatLng(-7.95755, 112.61347), new LatLng(-7.95777, 112.61353), new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.96094, 112.61364), new LatLng(-7.96175, 112.61339), new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.965, 112.61357), new LatLng(-7.97044, 112.6134), new LatLng(-7.97175, 112.61322),
                        new LatLng(-7.97196, 112.61329), new LatLng(-7.97315, 112.61313), new LatLng(-7.97374, 112.61304),
                        new LatLng(-7.97684, 112.61252), new LatLng(-7.97749, 112.61256), new LatLng(-7.97768, 112.61263),
                        new LatLng(-7.97829, 112.61291), new LatLng(-7.98006, 112.61412), new LatLng(-7.9814, 112.6147),
                        new LatLng(-7.98163, 112.61472), new LatLng(-7.98211, 112.61466), new LatLng(-7.98355, 112.61431),
                        new LatLng(-7.9834, 112.61501), new LatLng(-7.98334, 112.6156), new LatLng(-7.98354, 112.61884),
                        new LatLng(-7.98361, 112.6199), new LatLng(-7.98354, 112.62057), new LatLng(-7.98327, 112.62164),
                        new LatLng(-7.98323, 112.62204), new LatLng(-7.98359, 112.62444), new LatLng(-7.98407, 112.62611),
                        new LatLng(-7.98572, 112.62417), new LatLng(-7.98804, 112.62229), new LatLng(-7.98843, 112.62211),
                        new LatLng(-7.98892, 112.62203), new LatLng(-7.98998, 112.62171), new LatLng(-7.99094, 112.62141), new LatLng(-8.00117, 112.61788),
                        new LatLng(-8.00284, 112.61773), new LatLng(-8.00428, 112.61799), new LatLng(-8.00703, 112.61884),
                new LatLng(-8.01042, 112.62003),new LatLng(-8.0123, 112.6204),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01567, 112.62022),new LatLng(-8.01818, 112.61981),
                        new LatLng(-8.02015, 112.61906),new LatLng(-8.02036, 112.61955),new LatLng(-8.02194, 112.62391),new LatLng(-8.02248, 112.62621),new LatLng(-8.0228, 112.6276));

            }

            public void arjosari_sekolah_tinggi_teologi() {//at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984), new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701), new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.97941, 112.62548), new LatLng(-7.97924, 112.62527), new LatLng(-7.97922, 112.62517),
                        new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203), new LatLng(-7.97112, 112.61216), new LatLng(-7.971, 112.6122),
                        new LatLng(-7.96912, 112.61241), new LatLng(-7.96894, 112.61244), new LatLng(-7.96855, 112.61247),
                        new LatLng(-7.96837, 112.61249), new LatLng(-7.96699, 112.61284), new LatLng(-7.96683, 112.61228),
                        new LatLng(-7.96668, 112.61172), new LatLng(-7.96662, 112.61141), new LatLng(-7.96635, 112.61048),
                        new LatLng(-7.96624, 112.6098), new LatLng(-7.96587, 112.60783), new LatLng(-7.96577, 112.60735));
            }

            public void landungsari_sekolah_tinggi_teologi() {//gl-at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281), new LatLng(-7.97637, 112.62934), new LatLng(-7.97637, 112.62937),
                        new LatLng(-7.97647, 112.62937), new LatLng(-7.97647, 112.629), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762),new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701), new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.97941, 112.62548), new LatLng(-7.97924, 112.62527), new LatLng(-7.97922, 112.62517),
                        new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203), new LatLng(-7.97112, 112.61216), new LatLng(-7.971, 112.6122),
                        new LatLng(-7.96912, 112.61241), new LatLng(-7.96894, 112.61244), new LatLng(-7.96855, 112.61247),
                        new LatLng(-7.96837, 112.61249), new LatLng(-7.96699, 112.61284), new LatLng(-7.96683, 112.61228),
                        new LatLng(-7.96668, 112.61172), new LatLng(-7.96662, 112.61141), new LatLng(-7.96635, 112.61048),
                        new LatLng(-7.96624, 112.6098), new LatLng(-7.96587, 112.60783), new LatLng(-7.96577, 112.60735));
            }

            public void gadang_sekolah_tinggi_teologi() {//ldg-at
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955), new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981), new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036), new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204), new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797), new LatLng(-8.00277, 112.61773), new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517),new LatLng(-7.97837, 112.62405), new LatLng(-7.97728, 112.62266), new LatLng(-7.97716, 112.62246),
                        new LatLng(-7.97682, 112.62204), new LatLng(-7.97611, 112.62106), new LatLng(-7.9758, 112.62055),
                        new LatLng(-7.97448, 112.62088), new LatLng(-7.97268, 112.62133), new LatLng(-7.97242, 112.62045),
                        new LatLng(-7.97223, 112.6196), new LatLng(-7.97217, 112.61896), new LatLng(-7.9722, 112.61836),
                        new LatLng(-7.97238, 112.61759), new LatLng(-7.97253, 112.61714), new LatLng(-7.9727, 112.61681),
                        new LatLng(-7.973, 112.61615), new LatLng(-7.97289, 112.61601), new LatLng(-7.9727, 112.61577),
                        new LatLng(-7.97255, 112.61552), new LatLng(-7.97244, 112.6152), new LatLng(-7.97243, 112.61511),
                        new LatLng(-7.9723, 112.61504), new LatLng(-7.97204, 112.61468), new LatLng(-7.97175, 112.61418),
                        new LatLng(-7.97153, 112.61375), new LatLng(-7.97142, 112.61329), new LatLng(-7.97173, 112.61322),
                        new LatLng(-7.97182, 112.61319), new LatLng(-7.97168, 112.61266), new LatLng(-7.97161, 112.61248),
                        new LatLng(-7.97137, 112.61203), new LatLng(-7.97112, 112.61216), new LatLng(-7.971, 112.6122),
                        new LatLng(-7.96912, 112.61241), new LatLng(-7.96894, 112.61244), new LatLng(-7.96855, 112.61247),
                        new LatLng(-7.96837, 112.61249), new LatLng(-7.96699, 112.61284), new LatLng(-7.96683, 112.61228),
                        new LatLng(-7.96668, 112.61172), new LatLng(-7.96662, 112.61141), new LatLng(-7.96635, 112.61048),
                        new LatLng(-7.96624, 112.6098), new LatLng(-7.96587, 112.60783), new LatLng(-7.96577, 112.60735));
            }

            public void landungsari_stipak() {//gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262), new LatLng(-7.93176, 112.6026), new LatLng(-7.93211, 112.60271),
                        new LatLng(-7.93316, 112.60338), new LatLng(-7.93598, 112.60533), new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93963, 112.60786), new LatLng(-7.94313, 112.61032), new LatLng(-7.94451, 112.61131), new LatLng(-7.94616, 112.61238), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94908, 112.61462), new LatLng(-7.94985, 112.61536), new LatLng(-7.94996, 112.61545), new LatLng(-7.95291, 112.61903), new LatLng(-7.95461, 112.62051),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95784, 112.62342), new LatLng(-7.95989, 112.62543),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.96139, 112.62593), new LatLng(-7.9617, 112.62594),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.96214, 112.62584), new LatLng(-7.9622, 112.62581),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.96266, 112.62573), new LatLng(-7.96334, 112.62547),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96448, 112.62503), new LatLng(-7.96519, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96569, 112.62486), new LatLng(-7.96596, 112.62468),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96846, 112.62379), new LatLng(-7.96977, 112.62312),
                        new LatLng(-7.9719, 112.62194), new LatLng(-7.97213, 112.62186), new LatLng(-7.97225, 112.62195),
                        new LatLng(-7.97238, 112.62202), new LatLng(-7.97245, 112.62202), new LatLng(-7.97269, 112.62255),
                        new LatLng(-7.97316, 112.62349), new LatLng(-7.9734, 112.62405), new LatLng(-7.97403, 112.62488),
                        new LatLng(-7.97526, 112.62603), new LatLng(-7.97526, 112.62614), new LatLng(-7.97532, 112.62621),
                        new LatLng(-7.97541, 112.62624), new LatLng(-7.97548, 112.62623), new LatLng(-7.97557, 112.62636),
                        new LatLng(-7.97571, 112.62653), new LatLng(-7.97596, 112.62686), new LatLng(-7.97593, 112.62694), new LatLng(-7.97596, 112.62699),
                        new LatLng(-7.976, 112.62701), new LatLng(-7.97623, 112.62747), new LatLng(-7.9763, 112.62762),
                        new LatLng(-7.97636, 112.62773), new LatLng(-7.97642, 112.62788), new LatLng(-7.97645, 112.62804),
                        new LatLng(-7.97645, 112.6281),new LatLng(-7.97637, 112.62934),new LatLng(-7.97637, 112.62937),
                        new LatLng(-7.97647, 112.62937), new LatLng(-7.97647, 112.629), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.9772, 112.62749),
                        new LatLng(-7.97777, 112.62733), new LatLng(-7.97831, 112.62701),
                        new LatLng(-7.97919, 112.62658), new LatLng(-7.97953, 112.62635), new LatLng(-7.97987, 112.62615),
                        new LatLng(-7.98047, 112.62696), new LatLng(-7.98071, 112.62726), new LatLng(-7.9809, 112.62759));
            }

            public void arjosari_stipak() {//ga-gl
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97874, 112.63257), new LatLng(-7.9788, 112.6325), new LatLng(-7.97912, 112.63142),
                        new LatLng(-7.97936, 112.63118), new LatLng(-7.98025, 112.63089), new LatLng(-7.98046, 112.63066),
                        new LatLng(-7.98066, 112.6306), new LatLng(-7.98159, 112.63041), new LatLng(-7.98187, 112.63168),
                        new LatLng(-7.9836, 112.63133),new LatLng(-7.98305, 112.62923), new LatLng(-7.983, 112.62908), new LatLng(-7.98262, 112.62749));
            }

            public void gadang_stipak() {//ag
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.01686, 112.62839), new LatLng(-8.01388, 112.6288), new LatLng(-8.01359, 112.62883), new LatLng(-8.01272, 112.62899),
                        new LatLng(-8.01089, 112.6293), new LatLng(-8.0104, 112.62939),
                        new LatLng(-8.01014, 112.62942), new LatLng(-8.0093, 112.62955),
                        new LatLng(-8.00901, 112.62957), new LatLng(-8.00876, 112.62962),
                        new LatLng(-8.00378, 112.63043),new LatLng(-8.00006, 112.63174), new LatLng(-7.99974, 112.63189), new LatLng(-7.99874, 112.63225),
                        new LatLng(-7.9978, 112.6325), new LatLng(-7.99769, 112.63248), new LatLng(-7.9957, 112.63297),
                        new LatLng(-7.9956, 112.63297), new LatLng(-7.99399, 112.63338),new LatLng(-7.99352, 112.6335), new LatLng(-7.99306, 112.6319),
                        new LatLng(-7.99297, 112.63166), new LatLng(-7.99287, 112.63141),
                        new LatLng(-7.99266, 112.63122), new LatLng(-7.9925, 112.63113),
                        new LatLng(-7.99218, 112.63104),new LatLng(-7.9919, 112.63108), new LatLng(-7.99162, 112.63025), new LatLng(-7.99114, 112.62882),
                        new LatLng(-7.9909, 112.62813), new LatLng(-7.99083, 112.62801), new LatLng(-7.9907, 112.62787),
                        new LatLng(-7.98991, 112.62677),new LatLng(-7.98914, 112.6257), new LatLng(-7.98788, 112.62594), new LatLng(-7.98664, 112.62633),
                        new LatLng(-7.98599, 112.62657),new LatLng(-7.98604, 112.62677), new LatLng(-7.9862, 112.62728), new LatLng(-7.98664, 112.62856),
                        new LatLng(-7.98467, 112.62881), new LatLng(-7.98399, 112.62898), new LatLng(-7.98388, 112.62901),
                        new LatLng(-7.98305, 112.62924), new LatLng(-7.983, 112.62908), new LatLng(-7.98281, 112.62833),
                        new LatLng(-7.98262, 112.62751), new LatLng(-7.98247, 112.62693), new LatLng(-7.9809, 112.62759),
                        new LatLng(-7.98094, 112.62774), new LatLng(-7.981, 112.62805), new LatLng(-7.98107, 112.62835));
                setUpMapIfNeeded();
            }

            public void gadang_uniga() {//lg-jdm
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-8.0228, 112.6276),
                        new LatLng(-8.02248, 112.62621), new LatLng(-8.02194, 112.62391), new LatLng(-8.02036, 112.61955),new LatLng(-8.02015, 112.61906),
                        new LatLng(-8.01818, 112.61981),new LatLng(-8.01567, 112.62022),
                        new LatLng(-8.01398, 112.62036),new LatLng(-8.01355, 112.62046),
                        new LatLng(-8.0123, 112.6204),new LatLng(-8.01042, 112.62003),
                        new LatLng(-8.007, 112.61883),
                        new LatLng(-8.00427, 112.61797),new LatLng(-8.00277, 112.61773),new LatLng(-8.00142, 112.61781),
                        new LatLng(-8.00042, 112.61813), new LatLng(-8.00089, 112.61963), new LatLng(-8.00118, 112.6206),
                        new LatLng(-8.00132, 112.62105), new LatLng(-8.00188, 112.62313), new LatLng(-8.00191, 112.62327),
                        new LatLng(-8.00199, 112.62342), new LatLng(-8.00255, 112.62426), new LatLng(-8.00266, 112.62444),
                        new LatLng(-8.00272, 112.62462), new LatLng(-8.00281, 112.62549), new LatLng(-8.00273, 112.62741),
                        new LatLng(-8.00241, 112.62746), new LatLng(-8.0018, 112.62742), new LatLng(-8.0016, 112.62735),
                        new LatLng(-7.99997, 112.62657), new LatLng(-7.99972, 112.62654), new LatLng(-7.9995, 112.62661),
                        new LatLng(-7.99931, 112.62689), new LatLng(-7.99807, 112.62744), new LatLng(-7.99614, 112.62829),
                        new LatLng(-7.99549, 112.62686), new LatLng(-7.99418, 112.62745), new LatLng(-7.99113, 112.62882),
                        new LatLng(-7.99086, 112.62805), new LatLng(-7.98945, 112.62613), new LatLng(-7.98914, 112.6257),
                        new LatLng(-7.98843, 112.62581), new LatLng(-7.98787, 112.62594), new LatLng(-7.98736, 112.6248),
                        new LatLng(-7.98654, 112.62354), new LatLng(-7.98572, 112.62417), new LatLng(-7.98513, 112.62468),
                        new LatLng(-7.98461, 112.62529), new LatLng(-7.98407, 112.62611), new LatLng(-7.98408, 112.62621),
                        new LatLng(-7.98089, 112.62758), new LatLng(-7.98071, 112.62724), new LatLng(-7.97925, 112.62529),
                        new LatLng(-7.97922, 112.62517), new LatLng(-7.97838, 112.62409), new LatLng(-7.97728, 112.62267),
                        new LatLng(-7.97587, 112.62069), new LatLng(-7.97578, 112.62053), new LatLng(-7.97573, 112.62046),
                        new LatLng(-7.97513, 112.61904), new LatLng(-7.97461, 112.61746), new LatLng(-7.97431, 112.61676),
                        new LatLng(-7.97392, 112.61616), new LatLng(-7.97335, 112.61557), new LatLng(-7.97328, 112.6157),
                        new LatLng(-7.973, 112.61614), new LatLng(-7.97269, 112.61576), new LatLng(-7.97255, 112.61552),
                        new LatLng(-7.97244, 112.61522), new LatLng(-7.97243, 112.61511), new LatLng(-7.9723, 112.61505),
                        new LatLng(-7.97193, 112.6145), new LatLng(-7.97173, 112.61417), new LatLng(-7.97151, 112.61371),
                        new LatLng(-7.97142, 112.61329), new LatLng(-7.97085, 112.61338),
                        new LatLng(-7.97044, 112.6134),new LatLng(-7.965, 112.61357),new LatLng(-7.9621, 112.61335),
                        new LatLng(-7.96175, 112.61339),new LatLng(-7.96094, 112.61364),new LatLng(-7.96067, 112.61365),
                        new LatLng(-7.95777, 112.61353),new LatLng(-7.95755, 112.61347),new LatLng(-7.95679, 112.61286),new LatLng(-7.95665, 112.61275),new LatLng(-7.9543, 112.6106),
                        new LatLng(-7.95358, 112.61029),new LatLng(-7.95291, 112.6098),new LatLng(-7.95227, 112.60928),
                        new LatLng(-7.95208, 112.60919),new LatLng(-7.95147, 112.60901),new LatLng(-7.95141, 112.60899),
                        new LatLng(-7.95092, 112.60891),new LatLng(-7.95068, 112.60889),new LatLng(-7.94815, 112.60876),
                        new LatLng(-7.94797, 112.60875),new LatLng(-7.94738, 112.60881),new LatLng(-7.9471, 112.60883),
                        new LatLng(-7.94698, 112.60887),new LatLng(-7.94681, 112.60893),new LatLng(-7.9434, 112.61015),
                        new LatLng(-7.9432, 112.61025),new LatLng(-7.94313, 112.61032),new LatLng(-7.93963, 112.60786),new LatLng(-7.9377, 112.60652),
                        new LatLng(-7.93598, 112.60533),new LatLng(-7.93599, 112.60534),
                        new LatLng(-7.93667, 112.60424), new LatLng(-7.9367, 112.60412), new LatLng(-7.93665, 112.60365),
                        new LatLng(-7.93661, 112.60316),new LatLng(-7.93657, 112.60293), new LatLng(-7.93646, 112.60248), new LatLng(-7.93649, 112.60236),
                        new LatLng(-7.93656, 112.6023), new LatLng(-7.93695, 112.60228), new LatLng(-7.93722, 112.60229),
                        new LatLng(-7.93743, 112.60228),new LatLng(-7.93809, 112.60209), new LatLng(-7.93944, 112.60184), new LatLng(-7.93969, 112.60181),
                        new LatLng(-7.93984, 112.60177));
            }

            public void arjosari_uniga() {//al-jdm
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.93299, 112.65769),
                        new LatLng(-7.93285, 112.65687), new LatLng(-7.93212, 112.65512), new LatLng(-7.93027, 112.65068),
                        new LatLng(-7.92937, 112.6489), new LatLng(-7.93077, 112.6481), new LatLng(-7.93581, 112.64524),
                        new LatLng(-7.94315, 112.64165), new LatLng(-7.94546, 112.64075), new LatLng(-7.94753, 112.64012),
                        new LatLng(-7.95505, 112.6385), new LatLng(-7.95707, 112.63804), new LatLng(-7.96041, 112.637),
                        new LatLng(-7.96074, 112.63684), new LatLng(-7.96227, 112.63615), new LatLng(-7.96452, 112.63496),
                        new LatLng(-7.96478, 112.63549), new LatLng(-7.96552, 112.6368), new LatLng(-7.96598, 112.63761),
                        new LatLng(-7.96633, 112.63821), new LatLng(-7.9667, 112.63823), new LatLng(-7.96694, 112.63851),
                        new LatLng(-7.96708, 112.63873), new LatLng(-7.96736, 112.63862), new LatLng(-7.96759, 112.6383),
                        new LatLng(-7.96855, 112.63833), new LatLng(-7.97038, 112.63844), new LatLng(-7.97347, 112.63856),
                        new LatLng(-7.97346, 112.63788), new LatLng(-7.97338, 112.63667), new LatLng(-7.97747, 112.63683),
                        new LatLng(-7.97767, 112.63683), new LatLng(-7.9773, 112.63464), new LatLng(-7.97755, 112.63452),
                        new LatLng(-7.97768, 112.63431), new LatLng(-7.97773, 112.63397), new LatLng(-7.97758, 112.63361),
                        new LatLng(-7.97735, 112.63348), new LatLng(-7.9771, 112.63344), new LatLng(-7.97698, 112.63346),
                        new LatLng(-7.97656, 112.63156), new LatLng(-7.97651, 112.63015), new LatLng(-7.9765, 112.63006),
                        new LatLng(-7.97649, 112.62984),
                        new LatLng(-7.97648, 112.62946), new LatLng(-7.97649, 112.62858),
                        new LatLng(-7.97654, 112.62785), new LatLng(-7.97659, 112.62762), new LatLng(-7.97638, 112.62756),
                        new LatLng(-7.97631, 112.62749), new LatLng(-7.97607, 112.62699), new LatLng(-7.97609, 112.62693),
                        new LatLng(-7.97607, 112.62686), new LatLng(-7.97598, 112.62685), new LatLng(-7.97592, 112.62695),
                        new LatLng(-7.97539, 112.62711), new LatLng(-7.97462, 112.62727), new LatLng(-7.97435, 112.62731),
                        new LatLng(-7.97397, 112.62744), new LatLng(-7.97368, 112.62751), new LatLng(-7.97331, 112.62769),
                        new LatLng(-7.97292, 112.62794), new LatLng(-7.97272, 112.62803), new LatLng(-7.97268, 112.628),
                        new LatLng(-7.97192, 112.62669), new LatLng(-7.97186, 112.62664), new LatLng(-7.97179, 112.62663),
                        new LatLng(-7.97154, 112.62675), new LatLng(-7.97146, 112.62671), new LatLng(-7.97052, 112.625),
                        new LatLng(-7.97034, 112.62481), new LatLng(-7.97012, 112.62466), new LatLng(-7.96925, 112.62421),
                        new LatLng(-7.96873, 112.6239), new LatLng(-7.96851, 112.62387), new LatLng(-7.9684, 112.62364),
                        new LatLng(-7.96834, 112.62375), new LatLng(-7.96596, 112.62468), new LatLng(-7.96569, 112.62486),
                        new LatLng(-7.96546, 112.62487), new LatLng(-7.96519, 112.62486), new LatLng(-7.96448, 112.62503),
                        new LatLng(-7.96357, 112.62535), new LatLng(-7.96334, 112.62547), new LatLng(-7.96266, 112.62573),
                        new LatLng(-7.9623, 112.62584), new LatLng(-7.9622, 112.62581), new LatLng(-7.96214, 112.62584),
                        new LatLng(-7.96211, 112.62594), new LatLng(-7.9617, 112.62594), new LatLng(-7.96139, 112.62593),
                        new LatLng(-7.96053, 112.62577), new LatLng(-7.95989, 112.62543), new LatLng(-7.95784, 112.62342),
                        new LatLng(-7.95663, 112.62228), new LatLng(-7.95461, 112.62051), new LatLng(-7.95291, 112.61903),
                        new LatLng(-7.94996, 112.61545), new LatLng(-7.94985, 112.61536), new LatLng(-7.94839, 112.61404),
                        new LatLng(-7.94616, 112.61238), new LatLng(-7.94451, 112.61131),
                        new LatLng(-7.94214, 112.60957), new LatLng(-7.93964, 112.60786), new LatLng(-7.93729, 112.60623),
                        new LatLng(-7.93598, 112.60533),new LatLng(-7.93316, 112.60338),new LatLng(-7.93599, 112.60534),
                        new LatLng(-7.93667, 112.60424), new LatLng(-7.9367, 112.60412), new LatLng(-7.93665, 112.60365),
                        new LatLng(-7.93661, 112.60316),new LatLng(-7.93657, 112.60293), new LatLng(-7.93646, 112.60248), new LatLng(-7.93649, 112.60236),
                        new LatLng(-7.93656, 112.6023), new LatLng(-7.93695, 112.60228), new LatLng(-7.93722, 112.60229),
                        new LatLng(-7.93743, 112.60228),new LatLng(-7.93809, 112.60209), new LatLng(-7.93944, 112.60184), new LatLng(-7.93969, 112.60181),
                        new LatLng(-7.93984, 112.60177));
            }

            public void landungsari_uniga() {//gl-jdm
                mRouteExample = GoogleMapHelper.createMapRoute(new LatLng(-7.92489, 112.59844),
                        new LatLng(-7.92414, 112.599), new LatLng(-7.9252, 112.60042), new LatLng(-7.92739, 112.60266),
                        new LatLng(-7.92764, 112.60279), new LatLng(-7.92863, 112.60294), new LatLng(-7.92896, 112.60297),
                        new LatLng(-7.93145, 112.60262),new LatLng(-7.93211, 112.60271), new LatLng(-7.93316, 112.60338), new LatLng(-7.93599, 112.60534),
                        new LatLng(-7.93667, 112.60424), new LatLng(-7.9367, 112.60412), new LatLng(-7.93665, 112.60365),
                        new LatLng(-7.93661, 112.60316),new LatLng(-7.93657, 112.60293), new LatLng(-7.93646, 112.60248), new LatLng(-7.93649, 112.60236),
                        new LatLng(-7.93656, 112.6023), new LatLng(-7.93695, 112.60228), new LatLng(-7.93722, 112.60229),
                        new LatLng(-7.93743, 112.60228),new LatLng(-7.93809, 112.60209), new LatLng(-7.93944, 112.60184), new LatLng(-7.93969, 112.60181),
                        new LatLng(-7.93984, 112.60177));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        }



    @Override
    protected void onResume() {
        super.onResume();
        //init();
        //if (GoogleMapHelper.googleServicesAvailability(this) == null) {
            //setUpMapIfNeeded();
        //}
    }

    /**
     * Initialize UI elements, listeners etc.
     */
    private void init() {



    }

    /**
     * Creates a map instance if there isn't one already created
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap(false, false, false);
            }
        }
    }

    /**
     * Creation and customization of the map
     *
     * @param isIndoorEnabled      self explanatory
     * @param isAllGesturesEnabled self explanatory
     * @param isZoomControlEnabled self explanatory
     */
    private void setUpMap(boolean isIndoorEnabled, boolean isAllGesturesEnabled, boolean isZoomControlEnabled) {
        mMap.setIndoorEnabled(isIndoorEnabled);

        // Disable gestures & controls since ideal results (pause Animator) is
        // not easy to show in a simplified example.
        mMap.getUiSettings().setAllGesturesEnabled(isAllGesturesEnabled);
        mMap.getUiSettings().setZoomControlsEnabled(isZoomControlEnabled);

        // Create a marker to represent the user on the route.
        mMarker = mMap.addMarker(GoogleMapHelper.createMarker(mRouteExample[0], false,
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


//        mMap.addMarker(GoogleMapHelper.createMarker(mRouteExample[0], false,
//                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // Create a polyline for the route.
        mMap.addPolyline(GoogleMapHelper.createPolyline(mRouteExample, TestValues.POLYLINE_FINAL_COLOR,
                TestValues.POLYLINE_WIDTH));

        // Once the map is ready, zoom to the beginning of the route start the
        // animation.
        mMap.setOnMapLoadedCallback(this);

        // Move the camera over the start position.
        CameraPosition pos = GoogleMapAnimationHelper.createCameraPosition(mRouteExample,
                TestValues.CAMERA_OBLIQUE_ZOOM);

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
        mMap.setOnMapClickListener(this);
    }

    /**
     * When the map has finished loading all it's components (listener), calls the
     * GoogleMapsAnimationHelper.createRouteAnimatorSet() and starts animation (via callAnimateRoute()) method
     */
    @Override
    public void onMapLoaded() {
        // Once the camera has moved to the beginning of the route,
        // start the animation.
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                mMap.setOnCameraChangeListener(null);
                callAnimateRoute();
            }
        });

        // Animate the camera to the beginning of the route.
        CameraPosition pos = GoogleMapAnimationHelper.createCameraPosition(mRouteExample,
                TestValues.CAMERA_OBLIQUE_ZOOM, TestValues.CAMERA_OBLIQUE_TILT);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
    }

    /**
     * Calls the createRouteAnimatorSet, here to use the MapsActivity.this as the listener(s)
     * starts the animation.
     */
    private void callAnimateRoute() {

        mAnimatorSet = GoogleMapAnimationHelper.createRouteAnimatorSet(mRouteExample, mMap,
                TestValues.CAMERA_HEADING_CHANGE_RATE, mMarker, this, this, 0, 0);
        mAnimatorSet.start();
    }

    /**
     * Google Map animation listener mAnimatorSet
     */
    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mMap.moveCamera(CameraUpdateFactory
                .newCameraPosition(CameraPosition.builder(mMap.getCameraPosition())
                        .bearing((Float) valueAnimator.getAnimatedValue())
                        .build()));
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        Toast.makeText(getApplicationContext(), "Animation Cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        Toast.makeText(getApplicationContext(), "Welcome to the end", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        Toast.makeText(getApplicationContext(), "Animation Repeat", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        Toast.makeText(getApplicationContext(), "Animation Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        mMenu = menu; // Keep the menu for later use (swapping icons).
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mMap == null) {
            return true;
        }

        switch (item.getItemId()) {

            case R.id.action_marker:
                mMarker.setVisible(!mMarker.isVisible());
                return true;

            case R.id.action_buildings:
                mMap.setBuildingsEnabled(!mMap.isBuildingsEnabled());
                return true;

            case R.id.action_animation:
                if (mAnimatorSet.isRunning()) {
                    mAnimatorSet.cancel();
                } else {
                    mAnimatorSet.start();
                }
                return true;

            case R.id.action_perspective:

                CameraPosition currentPosition = mMap.getCameraPosition();
                CameraPosition newPosition;
                if (currentPosition.zoom == TestValues.CAMERA_OBLIQUE_ZOOM
                        && currentPosition.tilt == TestValues.CAMERA_OBLIQUE_TILT) {
                    newPosition = new CameraPosition.Builder()
                            .tilt(GoogleMapAnimationHelper.getMaximumTilt(19))
                            .zoom(19)
                            .bearing(currentPosition.bearing)
                            .target(currentPosition.target).build();
                } else {
                    newPosition = new CameraPosition.Builder()
                            .tilt(TestValues.CAMERA_OBLIQUE_TILT)
                            .zoom(TestValues.CAMERA_OBLIQUE_ZOOM)
                            .bearing(currentPosition.bearing)
                            .target(currentPosition.target).build();
                }
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(newPosition));

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        GoogleMapAnimationHelper.animateLiftOff(mMap, 2);
    }
}