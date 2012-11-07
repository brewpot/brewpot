package org.brewpot.service

import org.brewpot.web.Views
import com.ning.http.client.Request
import unfiltered.request.HttpRequest
import javax.servlet.http.HttpServletRequest

object MainService {

  def main(r: HttpRequest[HttpServletRequest]) = Views.main

}
