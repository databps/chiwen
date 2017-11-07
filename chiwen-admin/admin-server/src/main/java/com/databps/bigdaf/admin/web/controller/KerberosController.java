
package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.security.kerberos.execption.KerberosOperationException;
import com.databps.bigdaf.admin.service.SettingsService;
import com.databps.bigdaf.admin.vo.ConfigVo;
import com.databps.bigdaf.admin.vo.KerberosVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.databps.bigdaf.admin.domain.Kerberos;
import com.databps.bigdaf.admin.service.KerberosService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shibingxin on 2017/7/17.
 */
@Controller
@RequestMapping(value = "/kerberos")
public class KerberosController extends BaseController {

	@Autowired
	KerberosService kerberosService;
	@Autowired
	private File calcLauncherDir;
	@Autowired
	private SettingsService settingsService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {
		String  absolutePath = calcLauncherDir.getParent();
		//String cmpyId = getCmpyId(request);
		String cmpyId = "5968802a01cbaa46738eee3d";
		KerberosVo kerberos = kerberosService.findKerverosConfig(cmpyId);
		ConfigVo configVo = settingsService.getConfig(cmpyId);
		model.addAttribute("config",configVo);
		model.addAttribute("kerberos",kerberos);
		model.addAttribute("absolutePath",absolutePath);
		model.addAttribute("errors",null);
		return "kerberos/list";
	}


	/**
	 * @param kerberosVo
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated KerberosVo kerberosVo, HttpServletRequest request, Model model) {
		String cmpyId = null ;//getCmpyId(request);
		cmpyId = "5968802a01cbaa46738eee3d";
		try {
			kerberosVo.setCmpyId(cmpyId);
			String  absolutePath = calcLauncherDir.getParent();
			ConfigVo configVo = settingsService.getConfig(cmpyId);
			model.addAttribute("config",configVo);
			model.addAttribute("kerberos",kerberosVo);
			model.addAttribute("absolutePath",absolutePath);
			kerberosService.saveKerberosConf(kerberosVo);
		} catch (KerberosOperationException e) {
			model.addAttribute("errors","内部错误"+e.getMessage());
			e.printStackTrace();
		}catch (IOException e) {
			model.addAttribute("errors","内部错误"+e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			model.addAttribute("errors","内部错误请查看日志");
			e.printStackTrace();
		}
		return "kerberos/list";
	}


	@ResponseBody
	@RequestMapping(value = "/ping", method = RequestMethod.POST)
	public ResponseJson ping(@RequestParam(required = true) String host) {
		String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
		ResponseJson json = new ResponseJson();
		try {
			Integer result = kerberosService.ping(host);
			json.setCode(result);
		} catch (Exception e) {
			json.setCode(0);
			return json;
		}
		return json;
	}
}
